# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

`metamac-sso` is a **CAS (Central Authentication Service) overlay** for the SIEMAC/ISTAC eDatos platform, built on top of [Apereo CAS 7.3.0](https://github.com/apereo/cas/releases/tag/v7.3.0). It uses the standard CAS WAR overlay approach, adding custom configuration, service definitions, and a Spring Boot bootstrap class that integrates with the Metamac Common Metadata configuration system.

## Module Structure

```
metamac-sso-parent/   # Parent POM: dependency management (CAS BOM, Lombok, etc.)
metamac-sso-web/      # Main WAR module (the CAS overlay)
  src/main/java/      # Single custom class: CommonMetadataPropertySourceBootstrapConfiguration
  src/main/resources/
    application.yml                  # Main CAS configuration
    bootstrap.properties             # Spring Cloud Config bootstrap
    application-standalone.properties # SAML delegated auth config (commented examples)
    metamac/environment.xml          # Production environment template (fill-me placeholders)
    metamac/environment-LOCAL.xml    # Local dev environment (points to local PostgreSQL)
    services/custom/*.json           # CAS service registry definitions (hot-reload supported)
    metadata/*.xml                   # SAML SP metadata files (NO hot-reload)
    log4j2.xml                       # Logging config
dockerfiles/
  Dockerfile
  conf/                              # Docker container config template
    environment.xml
    application-env.yml
```

## Build Commands

```bash
# Build the WAR
mvn clean package

# Build and install to local Maven repo
mvn clean install

# Build Docker image (run from project root, after mvn clean package)
docker build -f dockerfiles/Dockerfile -t authentication-service:<version> .

# Run Docker container
docker run -i --rm -p 8080:8080 authentication-service:<version>

# Run with remote debug enabled
docker run -i --rm \
  -e JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n" \
  -p 8080:8080 -p 5005:5005 \
  authentication-service:<version>
```

There are no automated tests in this project.

## Key Architecture: Configuration Bootstrapping

The only custom Java class is `CommonMetadataPropertySourceBootstrapConfiguration` (`metamac-sso-web/src/main/java/.../config/`). It implements Spring Cloud's `PropertySourceLocator` and is registered as a bootstrap configuration via `META-INF/spring.factories`.

At startup, it:
1. Loads `metamac/environment.xml` (or the file at env var `AUTHENTICATION_SERVICE_CONF_PATH`) which points to the Common Metadata PostgreSQL database.
2. Reads all configuration properties from that database (via `ConfigurationServiceImpl`).
3. Decrypts any property whose key ends in `.password` using `MetamacEncryptor`.
4. Derives two additional properties from `metamac.security.cas_server_url_prefix`:
   - `generated.cas.server.name` — protocol + host + port
   - `generated.edatos.context` — URL path portion
5. Injects all properties into the Spring `Environment` so they are available for `application.yml` variable substitution.

This means `application.yml` properties like `${metamac.security.cas_server_url_prefix}`, `${metamac.security.delegated.login_url}`, and `${metamac.security.datasource.*}` are all sourced from the Common Metadata database.

## Local Development Configuration

For local development, edit `metamac-sso-web/src/main/resources/metamac/environment-LOCAL.xml` to set:
- `environment.metamac.data` — local data directory path
- `environment.metamac.configuration.db.*` — connection to the local Common Metadata PostgreSQL database

The bootstrap configuration picks up `environment-LOCAL.xml` when the classpath resource `metamac/environment.xml` is replaced at build time, or via the `AUTHENTICATION_SERVICE_CONF_PATH` environment variable.

**Lombok must be configured in your IDE** — missing Lombok support causes `PersonDirectoryPrincipalResolver` compilation errors.

## Service Registry (services/custom/)

Service definitions are JSON files following the CAS JSON Service Registry format. Key behaviors:
- Files in `services/custom/` support **automatic hot-reload** when modified directly on disk.
- Files in `metadata/` do **not** support hot-reload; restart is required.

The default service `HTTPSandHTTP-1.json` permits all HTTP/HTTPS URLs and uses Groovy inline scripts for attribute mapping (`username`, `displayName`, `email`, `acl`).

## Authentication Modes

The overlay supports two delegated authentication providers (configured in `application.yml`):
- **`DELEGATED_CAS`** — delegates to another CAS server (URL from `metamac.security.delegated.login_url`)
- **`SAML2EDatosSSOClient`** — SAML2 delegation (configuration examples in `application-standalone.properties`)

To force a specific provider when multiple are configured:
```
<sso-url>/clientredirect?client_name=DELEGATED_CAS
<sso-url>/clientredirect?client_name=SAML2EDatosSSOClient
```

The `cas.custom.properties.casAutoRedirect` property controls auto-redirect behavior (default: `SERVER`; set to `NONE` to show the provider selection UI).

## SAML IdP

CAS also acts as a SAML IdP. Its metadata is stored at runtime under `${environment.metamac.data}/sso/saml/idp` (configured via `cas.authn.saml-idp.metadata.file-system.location`).
