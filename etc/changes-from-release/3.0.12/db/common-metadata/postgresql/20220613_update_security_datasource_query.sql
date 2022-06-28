-- --------------------------------------------------------------------------------------------------
-- EDATOS-3641 - Integrar Tableau Server con CAS
-- EDATOS-3582 - Modificación CAS para enviar metadatos respuesta del CAS ISTAC
-- --------------------------------------------------------------------------------------------------

-- Modificada la consulta que expone los atributos desde el access control para exponer nuevos atributos
UPDATE tb_data_configurations
      SET conf_value='SELECT usr.username as username, 
            ''ACL'' as acl_name,  concat(app.code, ''#'', rol.code, ''#'',  operations.code) as acl_value,
            ''username'' as username_name,  username as username_value,
            ''name'' as name_name, name as name_value,
            ''surname'' as surname_name, surname as surname_value,
            ''mail'' as mail_name, mail as mail_value
        FROM TB_ACCESS acc
        LEFT JOIN TB_USERS usr ON acc.USER_FK = usr.ID
        LEFT JOIN TB_ROLES rol ON acc.ROLE_FK = rol.ID
        LEFT JOIN TB_APPS app ON acc.APP_FK = app.ID
        LEFT JOIN TB_EXTERNAL_ITEMS operations ON acc.OPERATION_FK = operations.ID
        WHERE REMOVAL_DATE IS NULL AND {0}
    '
where conf_key = 'metamac.security.datasource.query.attributes';

commit;