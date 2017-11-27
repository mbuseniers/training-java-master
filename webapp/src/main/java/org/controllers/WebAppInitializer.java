package org.controllers;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@Configuration
@ComponentScan({"org.controllers", "org.core", "org.service", "org.persistence" })
public class WebAppInitializer implements WebApplicationInitializer {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebAppInitializer.class);

	@Override
	public void onStartup(ServletContext container) {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation("org.controllers");

		container.addListener(new ContextLoaderListener(context));

		ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(context));

		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/dashboard");
		dispatcher.addMapping("/addcomputer");
		dispatcher.addMapping("/editcomputer");
		dispatcher.addMapping("/deletecomputers");

	}
	
	@Bean
	public MessageSource messageSource() {
		LOGGER.info("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("messages");
		return messageSource;
	}

	@Bean
	public ViewResolver internalResourceViewResolver() {
		LOGGER.info("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
		bean.setViewClass(JstlView.class);
		bean.setPrefix("/views/");
		bean.setSuffix(".jsp");
		return bean;
	}

}
