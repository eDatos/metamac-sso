-- --------------------------------------------------------------------------------------------------
-- EDATOS-5037 - Borrado de propiedades deprecadas del CAS en el common-metadata
--
-- Se eliminan las propiedades deprecadas del common-metadata
-- --------------------------------------------------------------------------------------------------

DELETE FROM TB_DATA_CONFIGURATIONS
WHERE conf_key IN (
                   'deprecated.metamac.security.ldap.url',
                   'deprecated.metamac.security.ldap.user_dn',
                   'deprecated.metamac.security.ldap.password',
                   'deprecated.metamac.security.ldap.filter',
                   'deprecated.metamac.security.ldap.search_base',
                   'deprecated.server.prefix',
                   'deprecated.cas.securityContext.serviceProperties.service',
                   'deprecated.cas.securityContext.serviceProperties.adminRoles',
                   'deprecated.cas.securityContext.casProcessingFilterEntryPoint.loginUrl',
                   'deprecated.cas.securityContext.ticketValidator.casServerUrlPrefix',
                   'deprecated.cas.themeResolver.defaultThemeName',
                   'deprecated.cas.viewResolver.basename',
                   'deprecated.host.name',
                   'deprecated.database.hibernate.dialect'
    );

COMMIT;