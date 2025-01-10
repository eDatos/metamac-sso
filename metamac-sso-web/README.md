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
