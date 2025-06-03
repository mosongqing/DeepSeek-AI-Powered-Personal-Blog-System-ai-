package com.example.studytwo.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;

import java.net.InetAddress;

@Configuration
public class HttpConfig implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        if (factory instanceof TomcatServletWebServerFactory tomcatFactory) {
            tomcatFactory.addAdditionalTomcatConnectors(redirectConnector());
        }
    }

    private org.apache.catalina.connector.Connector redirectConnector() {
        org.apache.catalina.connector.Connector connector =
                new org.apache.catalina.connector.Connector(org.apache.coyote.http11.Http11NioProtocol.class.getName());
        connector.setScheme("http");
        connector.setPort(8080);              // ⚠️ 这里是你想监听的 HTTP 端口
        connector.setSecure(false);
        return connector;
    }
}
