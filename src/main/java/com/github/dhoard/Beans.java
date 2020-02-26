package com.github.dhoard;

import com.github.dhoard.servlet.DefaultServlet;
import java.io.IOException;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Beans {

    @Bean(name="configuration")
    public Configuration createConfiguration() {
        return new Configuration();
    }

    @Bean(name="configurableServletWebServerFactory")
    public ConfigurableServletWebServerFactory createConfigurableServletWebServerFactory(Configuration configuration) throws Exception {
        TomcatServletWebServerFactory tomcatContainerFactory = new TomcatServletWebServerFactory();
        tomcatContainerFactory.setPort(
            configuration.getValue("PORT", (Integer) 8080));

        return tomcatContainerFactory;
    }

    @Bean(name="servletRegistrationBean2")
    public ServletRegistrationBean createServletReqistrationBean2(Configuration configuration) {
        DefaultServlet defaultServlet = new DefaultServlet();
        defaultServlet.setConfiguration(configuration);

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(defaultServlet, "/*");
        servletRegistrationBean.setLoadOnStartup(1);

        return servletRegistrationBean;
    }
}
