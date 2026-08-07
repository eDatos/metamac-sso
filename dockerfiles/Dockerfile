###
# Dockerfile usado para consutruir la imagen del authentication-service
#
# Antes de construir la imagen debemos haber compilado previamente el proyecto (desde el directorio raíz):
#
# mvn clean package
#
# A continuación podremos construir la imagen ejecutando siempre desde el directorio raíz del proyecto:
#
# docker build -f dockerfiles/Dockerfile -t authentication-service:<version> .
#
# Para ejecutar un contenedor asociado a esta imagen:
# docker run -i --rm -p 8080:8080 authentication-service:<version>
#
# Para ejecutar con opciones de debug habilitadas:
# docker run -i --rm \
# -e JAVA_OPTS="-agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n" \
# -p 8080:8080 \
# -p 5005:5005 \
# authentication-service:<version>
###

FROM eclipse-temurin:21-jdk
LABEL MAINTAINER=info@arte-consultores.com

RUN mkdir -p /data/edatos/sso/conf/oidc
RUN mkdir -p /data/edatos/sso/logs
RUN mkdir -p /data/edatos/sso/saml/idp

COPY dockerfiles/conf /data/edatos/sso/conf
COPY metamac-sso-web/src/main/resources/services/custom /data/edatos/sso/conf/services/custom
COPY metamac-sso-web/target/*.war /data/edatos/sso/authentication-service-internal.war

# Definir variables de entorno con ubicaciones externas de configuración
ENV SPRING_PROFILES_ACTIVE=env
ENV SPRING_CONFIG_LOCATION=classpath:/application.yml,file:/data/edatos/sso/conf/
ENV DATA_LOCATION_CONFIG=file:/data/edatos/sso/conf/data-location.properties
ENV AUTHENTICATION_SERVICE_CONF_PATH='/data/edatos/sso/conf/environment.xml'
ENV LANG='es_ES.UTF-8'

RUN env

# Exponer el puerto típico de Spring Boot
EXPOSE 8080

ENTRYPOINT ["java", "-Dlog.dir=/data/edatos/sso/logs", "-jar", "/data/edatos/sso/authentication-service-internal.war"]
