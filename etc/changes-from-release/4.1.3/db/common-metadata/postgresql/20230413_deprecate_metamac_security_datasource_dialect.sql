-- --------------------------------------------------------------------------------------------------
-- EDATOS-3324 - [CORE] Comprobar si se puede eliminar el dialecto de las propiedades de BBDD de todas las aplicaciones
-- 
-- Se depreca la propiedad con el valor metamac.security.datasource.dialect
-- --------------------------------------------------------------------------------------------------

update tb_data_configurations set conf_key = 'deprecated.metamac.security.datasource.dialect' where conf_key ='metamac.security.datasource.dialect';
commit;
