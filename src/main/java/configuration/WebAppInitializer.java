package configuration;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.Bean;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@EnableWebMvc
public class WebAppInitializer implements WebApplicationInitializer {

	@Override
    public void onStartup(ServletContext container) {
		AnnotationConfigWebApplicationContext context
        = new AnnotationConfigWebApplicationContext();
      context.setConfigLocation("configuration");

      container.addListener(new ContextLoaderListener(context));

      ServletRegistration.Dynamic dispatcher = container
        .addServlet("dispatcher", new DispatcherServlet(context));
       
      dispatcher.setLoadOnStartup(1);
      dispatcher.addMapping("/dashboard");
      dispatcher.addMapping("/addcomputer");
      dispatcher.addMapping("/editcomputer");

	}



}
