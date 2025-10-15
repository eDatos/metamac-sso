# Errores durante desarrollo
Si en el log encuentra el siguiente error confirme que ha configurado Lombok en su IDE ( https://confluence.arte.dev/display/CONOCIMIENTO/Lombok ) 
```
Caused by: java.lang.Error: Unresolved compilation problems: 
    The type PersonDirectoryPrincipalResolver must implement the inherited abstract method PrincipalResolver.getAttributeRepository()
    The constructor PersonDirectoryPrincipalResolver(StubPersonAttributeDao, PrincipalFactory, boolean, String::trim, null) is undefined
    The constructor PersonDirectoryPrincipalResolver(IPersonAttributeDao, PrincipalFactory, boolean, String::trim, String) is undefined
    The constructor PersonDirectoryPrincipalResolver(IPersonAttributeDao, PrincipalFactory, boolean, String::trim, null) is undefined
    The constructor PersonDirectoryPrincipalResolver(StubPersonAttributeDao, PrincipalFactory, boolean, String::trim, String) is undefined
    The constructor PersonDirectoryPrincipalResolver(IPersonAttributeDao, PrincipalFactory, boolean, (<no type> formUserId) -> {}, String) is undefined
```

# Tips durante desarrollo
Eliminar el archivo /metamac-sso-web/src/main/resources/templates/layout.html simplifica la depuración en desarrollo de las integraciones de terceros porque permite visualizar el Principal resuelto

# Sobre la carga automática de propiedades tras modificar ficheros dinámicamente

* Modificar directamente los archivos de services/custom/*.json, se refleja automáticamente
* Modificar directamente los archivos de metadata/*.xml, NO se reflejan automáticamente

# URLs para pruebas
En situaciones donde tenemos configurada de manera múltiple la autenticación delegada, podemos recurrir a esta URL para activar uno u otro,
dado que la botonera que está para estos casos nosotros no la dibujamos.

    <url sso>/clientredirect?client_name=DELEGATED_CAS
    <url sso>/clientredirect?client_name=SAML2EDatosSSOClient

