# UPGRADE - Proceso de actualización entre versiones

*Para actualizar de una versión a otra es suficiente con actualizar el WAR a la última versión. El siguiente listado presenta aquellos cambios de versión en los que no es suficiente con actualizar y que requieren por parte del instalador tener más cosas en cuenta. Si el cambio de versión engloba varios cambios de versión del listado, estos han de ejecutarse en orden de más antiguo a más reciente.*

*De esta forma, si tuviéramos una instalación en una versión **A.B.C** y quisieramos actualizar a una versión posterior **X.Y.Z** para la cual existan versiones anteriores que incluyan cambios listados en este documento, se deberá realizar la actualización pasando por todas estas versiones antes de poder llegar a la versión deseada.*

*EJEMPLO: Queremos actualizar desde la versión 1.0.0 a la 3.0.0 y existe un cambio en la base de datos en la actualización de la versión 1.0.0 a la 2.0.0.*

*Se deberá realizar primero la actualización de la versión 1.0.0 a la 2.0.0 y luego desde la 2.0.0 a la 3.0.0*

## 5.0.0 a 5.0.1-SNAPSHOT

Es necesario ejecutar los scripts SQL contenidos en la **carpeta para el borrado de propiedades deprecadas en common-metadata**
  ```shell
  etc/changes-from-release/5.0.0
  ```


## 4.11.2 a 5.0.0
*IMPORTANTE* Hace falta al menos un Tomcat 11 y Java 21

Comprobar que las carpetas referidas en los archivos de configuración tengan los valores correctos y existan

Si estamos ante el caso donde migramos de un tomcat edatos-internal a uno edatos-cas como solemos montar, habremos de hacer esto:

    mkdir -p /data/edatos-cas/sso
    # Limpiar los logs primero de /data/edatos-internal/sso/logs
    cp -a /data/edatos-internal/sso/. /data/edatos-cas/sso
    chown -R edatos-cas:edatos-cas /data/edatos-cas/sso  

### Cambios de configuración
Actualizar los log4j2.xml siguiendo el archivo incluido como referencia. Los cambios son:
 - Revisar carpeta de log por si ha cambiado al haber cambiado el tomcat
 - Eliminar packages="org.apereo.cas.logging"
 - Eliminar los cas_audit.log y los perfStarts.log
 - Añadidos loggers para org.apareo.inspektr y org.siemac.metamac

Actualizar las propiedades de configuración acorde a los cambios de la nueva versión. Usar los archivos proveídos como referencia. Algunas de las propiedades que cambian:
 - cas.authn.pac4j.saml[0].identity-provider-metadata-path a cas.authn.pac4j.saml[0].metadata.identity-provider-metadata-path
 - cas.authn.pac4j.principalAttributeId a cas.authn.pac4j.core.principal-id-attribute
 - cas.custom.properties.casAutoRedirect: false a cas.custom.properties.casAutoRedirect: NONE
 - cas.authn.pac4j.saml[0].autoRedirect: true a cas.authn.pac4j.saml[0].autoRedirectType: SERVER
 - cas.authn.pac4j.cas[0].autoRedirect a cas.authn.pac4j.cas[0].autoRedirectType
 - cas.server.name pasa a ser calculada
 - cas.authn.attributeRepository.expirationTime a cas.authn.attribute-repository.core.expiration-time
 - cas.authn.samlIdp.metadata.location a cas.authn.saml-idp.metadata.file-system.location
 - cas.authn.samlIdp.entityId a cas.authn.saml-idp.core.entity-id
 - cas.authn.samlIdp.scope a cas.server.scope
 - Las propiedades de firmado, muchas han cambiado, incluido el modo de generarlas, lo más rápido es eliminar las existentes en la configuración, arrancarla dejando que las genere, y pegarlas con los valores generado

Nuevas propiedades configuradas internamente:
 - server.forward-headers-strategy: framework
 - spring.main.allow-bean-definition-overriding: true
 - cas.person-directory.principal-transformation.case-conversion: LOWERCASE
 - cas.locale.cookie.path: ${edatos.context}

Propiedades eliminadas
 - cas.authn.oidc.claimsMap.email, ya no damos soporte a OIDC
 - cas.authn.oidc.jwks-file, ya no damos soporte a OIDC
 - cas.authn.oauth.userProfileViewType: FLAT, ya no está soportada, en su lugar se configura en los servicios
  
Actualizados servicios, usar los contenidos de services/custom como referencia del valor correcto que han de tomar. Han cambiado:
 - HTTPSandHTTP-1.json
 - TableauServer-2.json
 - openmetadata-3.json
 - Grafana-4.json
 - istacOrg-5.json
 - superset-6.json


## 4.6.4 a 4.7.0
* Se ha modificado el nombre y ruta de ciertas configuraciones a fin de presentar un aspecto más estándar y ordenado
- Se ha de mover el contenido de file:${environment.metamac.data}/sso/saml a file:${environment.metamac.data}/sso/saml/idp
- Se ha renombrado file:${environment.metamac.data}/sso/config/keystore.jwks  como file:${environment.metamac.data}/sso/config/oidc-keystore.jwks
- En entornos donde se usa SAML como SP puede ser necesario crear carpetas adicionales, como file:${environment.metamac.data}/sso/saml/sp, consultar el archivo application-standalone.properties para ver como configurarlo

## 4.3.1 a 4.4.0
- Se han realizado cambios a la base de datos, por ello se proveen una serie de scripts SQL para adaptarse a la nueva versión. 
  Ejecutar los scripts de la siguiente ruta en el esquema correspondiente por orden de fecha: [etc/changes-from-release/4.3.1/db/common-metadata/postgresql/](etc/changes-from-release/4.3.1/db/common-metadata/postgresql)

## 4.2.1 a 4.3.0
- Se han realizado cambios a la base de datos, por ello se proveen una serie de scripts SQL para adaptarse a la nueva versión. 
  Ejecutar los scripts de la siguiente ruta en el esquema correspondiente por orden de fecha: [etc/changes-from-release/4.2.1/db/common-metadata/postgresql/](etc/changes-from-release/4.2.1/db/common-metadata/postgresql)

## 4.0.1 a 4.1.0
Es necesario ejecutar los scripts SQL del proyecto metamac-sso contenidos en la carpeta
  ```shell
  etc/changes-from-release/4.0.1
  ```

En esta versión se implementa la resolución de atributos en la respuesta CAS para gestionar los roles de usuarios de CKAN en el Gestor de Accesos de EDATOS.

## 3.0.12 a 4.0.0
A partir de esta versión de la aplicación se elimina el soporte para bases de datos Oracle o Sql Server, siendo PostgreSQL la única base de datos con soporte.

Es necesario ejecutar los scripts SQL del proyecto metamac-sso contenidos en la carpeta
  ```shell
  etc/changes-from-release/3.0.12
  ```

En esta versión se le da soporte al SP de edatos-tableau-server que debe configurarse mediante el archivo /metadata/sp-tableau-server-metadata.xml

## 0.0.0 a 3.0.5
* El proceso de actualizaciones entre versiones para versiones anteriores a la 3.0.5 está definido en "Metamac - Manual de instalación.doc"
