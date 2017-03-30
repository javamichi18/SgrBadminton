package de.soflimo.sgr.config;

import org.h2.server.web.WebServlet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty("spring.h2.console.enabled")
public class WebConfiguration {
    @Bean
    ServletRegistrationBean h2servletRegistration () {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
        registrationBean.addUrlMappings("/console/*");

        return registrationBean;
    }
}
