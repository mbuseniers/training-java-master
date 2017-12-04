package org.webservice;

import org.omg.IOP.ServiceContext;
import org.persistence.PersistenceConfiguration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	  @Override
	  protected Class[] getRootConfigClasses() {
	    return new Class[] { WebServiceContext.class,PersistenceConfiguration.class,ServiceContext.class };
	  }

	  @Override
	  protected Class[] getServletConfigClasses() {
	    return null;
	  }

	  @Override
	  protected String[] getServletMappings() {
	    return new String[] { "/" };
	  }

	}