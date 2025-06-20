package org.siemac.metamac.cas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(exclude = {
org.springframework.boot.actuate.autoconfigure.MetricFilterAutoConfiguration.class,
org.springframework.boot.actuate.autoconfigure.MetricsDropwizardAutoConfiguration.class
})
public class MetamacSsoWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(MetamacSsoWebApplication.class, args);
    }
}
