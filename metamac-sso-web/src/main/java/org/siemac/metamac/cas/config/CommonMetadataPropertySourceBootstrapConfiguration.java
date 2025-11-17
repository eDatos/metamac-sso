package org.siemac.metamac.cas.config;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.siemac.metamac.core.common.conf.ConfigurationServiceImpl;
import org.siemac.metamac.core.common.util.MetamacEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Configuration
public class CommonMetadataPropertySourceBootstrapConfiguration implements PropertySourceLocator {

    static final Logger LOGGER = LoggerFactory.getLogger(CommonMetadataPropertySourceBootstrapConfiguration.class);

    @Bean("configurationService")
    public ConfigurationServiceImpl configurationService() {
        ConfigurationServiceImpl configurationServiceImpl = new ConfigurationServiceImpl();

        String environmentPath = System.getenv("AUTHENTICATION_SERVICE_CONF_PATH");
        //@formatter:off
        Resource resource = (environmentPath != null && new File(environmentPath).isFile())
                ? new FileSystemResource(environmentPath)
                : new ClassPathResource("metamac/environment.xml");
        //@formatter:on
        configurationServiceImpl.setSystemConfigurationFile(resource);

        return configurationServiceImpl;
    }

    @Override
    public PropertySource<?> locate(Environment environment) {
        Properties decodedProperties = process(configurationService().getProperties());
        return new PropertiesPropertySource(getClass().getSimpleName(), decodedProperties);
    }

    private Properties process(Properties properties) {
        Properties processedProperties = new Properties();
        for (String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            if (key.endsWith(".password")) {
                try {
                    processedProperties.setProperty(key, MetamacEncryptor.decrypt(value));
                } catch (Exception e) {
                    LOGGER.warn("An error has ocurred decrypting password property: " + key, e.getMessage());
                    processedProperties.setProperty(key, StringUtils.EMPTY);
                }
            } else {
                switch (key) {
                    case "metamac.security.cas_server_url_prefix":
                        try {
                            URI uri = new URI(value);
                            URL url = uri.toURL();

                            String casServerName = buildCasServerName(url);
                            processedProperties.setProperty("generated.cas.server.name", casServerName);
                            LOGGER.info("Generated property 'generated.cas.server.name' with value: " + casServerName);

                            String edatosContext = url.getPath();
                            processedProperties.setProperty("generated.edatos.context", edatosContext);
                            LOGGER.info("Generated property 'generated.edatos.context' with value: " + edatosContext);
                        } catch (URISyntaxException | MalformedURLException e) {
                            LOGGER.error("An error has ocurred parsing URL: " + value, e);
                            e.printStackTrace();
                        }
                    default:
                        processedProperties.setProperty(key, properties.getProperty(key));
                        break;
                }
            }
        }
        return processedProperties;
    }

    private String buildCasServerName(URL url) {
        String casServerName = url.getProtocol() + "://" + url.getHost();
        if (url.getPort() != -1) {
            casServerName += ":" + url.getPort();
        }
        return casServerName;
    }

}
