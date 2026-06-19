# dockerfiles

Contiene el `Dockerfile` y los ficheros de configuración que se hornean en la imagen Docker del `authentication-service`.

## Estructura

```
dockerfiles/
├── Dockerfile
└── conf/                              # Copiado íntegramente a /data/edatos/authentication-service/conf/
    ├── environment.xml                # Conexión a la BD de Common Metadata (rellenar antes del despliegue)
    ├── application-env.yml            # Overrides de Spring Boot activos con el perfil "env" (Docker)
    ├── metadata/                      # Metadatos SAML de los SPs registrados (sin hot-reload: requiere reinicio)
    │   ├── sp-openmetadata-metadata.xml
    │   └── sp-tableau-server-metadata.xml
    └── services/
        └── custom/                    # Servicios CAS por defecto incluidos en la imagen (con hot-reload)
            └── oidc-7.json
```

### Nota sobre `services/custom/`

Los ficheros JSON de `conf/services/custom/` son los **servicios CAS que se incluyen por defecto en la imagen**.
Son el espejo de `metamac-sso-web/src/main/resources/services/custom/`: cualquier servicio nuevo que deba
estar disponible en el despliegue Docker debe añadirse también aquí.

Si en producción el directorio `services/custom` se monta como volumen externo, los ficheros cargados
en la imagen quedan tapados por el montaje y hay que asegurarse de incluirlos en dicho volumen. 

## Generar la imagen para despliegue local

### 1. Compilar el proyecto

Desde el directorio raíz del repositorio:

```bash
mvn clean package
```

### 2. Construir la imagen Docker

```bash
docker build -f dockerfiles/Dockerfile -t authentication-service:<version> .
```

Sustituye `<version>` por la versión deseada, por ejemplo `5.1.1-SNAPSHOT`.

### 3. Configurar `environment.xml`

Antes de ejecutar el contenedor, edita `dockerfiles/conf/environment.xml` con los datos de conexión
a la BD de Common Metadata de tu entorno local. Este fichero se monta en
`/data/edatos/authentication-service/conf/environment.xml` dentro del contenedor.

### 4. Ejecutar el contenedor

```bash
docker run -i --rm -p 8080:8080 authentication-service:<version>
```

Con debug remoto habilitado (puerto 5005):

```bash
docker run -i --rm \
  -e JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n" \
  -p 8080:8080 \
  -p 5005:5005 \
  authentication-service:<version>
```

El servicio quedará disponible en `http://localhost:8080`.
