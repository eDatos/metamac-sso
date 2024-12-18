# UPGRADE - Proceso de actualización entre versiones

*Para actualizar de una versión a otra es suficiente con actualizar el WAR a la última versión. El siguiente listado presenta aquellos cambios de versión en los que no es suficiente con actualizar y que requieren por parte del instalador tener más cosas en cuenta. Si el cambio de versión engloba varios cambios de versión del listado, estos han de ejecutarse en orden de más antiguo a más reciente.*

*De esta forma, si tuviéramos una instalación en una versión **A.B.C** y quisieramos actualizar a una versión posterior **X.Y.Z** para la cual existan versiones anteriores que incluyan cambios listados en este documento, se deberá realizar la actualización pasando por todas estas versiones antes de poder llegar a la versión deseada.*

*EJEMPLO: Queremos actualizar desde la versión 1.0.0 a la 3.0.0 y existe un cambio en la base de datos en la actualización de la versión 1.0.0 a la 2.0.0.*

*Se deberá realizar primero la actualización de la versión 1.0.0 a la 2.0.0 y luego desde la 2.0.0 a la 3.0.0*

## 4.6.1 a 4.6.2-SNAPSHOT
* Se ha modificado el nombre y ruta de ciertas configuraciones a fin de presentar un aspecto más estándar y ordenado
- Se ha de mover el contenido de file:${environment.metamac.data}/sso/saml a file:${environment.metamac.data}/sso/saml/idp
- Se ha renombrado file:${environment.metamac.data}/sso/config/keystore.jwks  como file:${environment.metamac.data}/sso/config/oidc-keystore.jwks

## 0.0.0 a 4.6.1
* El proceso de actualizaciones entre versiones para versiones anteriores a la 4.6.1 está definido en "Metamac - Manual de instalación.doc"