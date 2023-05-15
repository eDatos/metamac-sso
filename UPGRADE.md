# UPGRADE - Proceso de actualización entre versiones

*Para actualizar de una versión a otra es suficiente con actualizar el WAR a la última versión. El siguiente listado presenta aquellos cambios de versión en los que no es suficiente con actualizar y que requieren por parte del instalador tener más cosas en cuenta. Si el cambio de versión engloba varios cambios de versión del listado, estos han de ejecutarse en orden de más antiguo a más reciente.*

*De esta forma, si tuviéramos una instalación en una versión **A.B.C** y quisieramos actualizar a una versión posterior **X.Y.Z** para la cual existan versiones anteriores que incluyan cambios listados en este documento, se deberá realizar la actualización pasando por todas estas versiones antes de poder llegar a la versión deseada.*

*EJEMPLO: Queremos actualizar desde la versión 1.0.0 a la 3.0.0 y existe un cambio en la base de datos en la actualización de la versión 1.0.0 a la 2.0.0.*

*Se deberá realizar primero la actualización de la versión 1.0.0 a la 2.0.0 y luego desde la 2.0.0 a la 3.0.0*

## ## 4.2.1 a 4.2.2-SNAPSHOT
- Se han realizado cambios a la base de datos, por ello se proveen una serie de scripts SQL para adaptarse a la nueva versión. 
  Ejecutar los scripts de la siguiente ruta en el esquema correspondiente por orden de fecha: [etc/changes-from-release/4.2.1/db/common-metadata/postgresql/](etc/changes-from-release/4.2.1/db/common-metadata/postgresql)
  
## 4.1.3 a 4.1.4-SNAPSHOT
- Se han realizado cambios a la base de datos, por ello se proveen una serie de scripts SQL para adaptarse a la nueva versión. 
  Ejecutar los scripts de la siguiente ruta en el esquema correspondiente por orden de fecha: [etc/changes-from-release/4.1.3/db/common-metadata/postgresql/](etc/changes-from-release/4.1.3/db/common-metadata/postgresql)

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
