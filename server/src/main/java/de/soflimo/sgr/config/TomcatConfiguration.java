package de.soflimo.sgr.config;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;

/**
 *
 */
public class TomcatConfiguration {

    //private static final log=LoggerFactory.getLogger(ServletContainerConfiguration)

    @Value("${tomcat.ajp.port}")
    private int ajpPort;

    @Value("${tomcat.ajp.enabled}")
    private boolean ajpEnabled;

    @Value("${tomcat.ajp.contextName}")
    private String contextName;


    @Bean
    public EmbeddedServletContainerFactory servletContainer () {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();

        if (contextName != null)
            factory.setContextPath(contextName);

        if (ajpEnabled) {
            Connector ajpConnector = new Connector("AJP/1.3");

            ajpConnector.setProtocol("AJP/1.3");
            ajpConnector.setPort(ajpPort);
            ajpConnector.setSecure(true);
            ajpConnector.setAllowTrace(false);
            ajpConnector.setScheme("https");
            //ajpConnector.setRedirectPort(8443);
            ajpConnector.setSecure(true);

            factory.addAdditionalTomcatConnectors(ajpConnector);
        }

        return factory;
    }
}
