package org.controllers;



import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.persistence.PersistenceConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@EnableWebMvc
@Configuration
@ComponentScan({ "org.console", "org.core", "org.persistence", "org.service", "org.controllers" })
public class WebAppInitializer implements WebApplicationInitializer {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceConfiguration.class);

	
	@Override
    public void onStartup(ServletContext container) {
		
		LOGGER.info("onStartUpaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		AnnotationConfigWebApplicationContext context
        = new AnnotationConfigWebApplicationContext();
      context.setConfigLocation("org.controllers");

      container.addListener(new ContextLoaderListener(context));

      ServletRegistration.Dynamic dispatcher = container
        .addServlet("dispatcher", new DispatcherServlet(context));
       
		dispatcher.setLoadOnStartup(1);
      dispatcher.addMapping("/dashboard");
      dispatcher.addMapping("/addcomputer");
      dispatcher.addMapping("/editcomputer");
      dispatcher.addMapping("/deletecomputers");


	}



}
