package com.mvc.controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


public class WebServletConfiguration implements WebApplicationInitializer {

	public void onStartup(ServletContext servletContext) throws ServletException {

		/**** For Specifying Application Context Programmatically ***
		AnnotationConfigWebApplicationContext appContext = 
				new AnnotationConfigWebApplicationContext();
		appContext.register(ApplicationContextConfiguration.class);
		**/
		
		/*** For Specifying Application Context Using XML ***
		 XmlWebApplicationContext appContext = new XmlWebApplicationContext();
		 appContext.setConfigLocation("/WEB-INF/application-context.xml");		 
		 **/
		
		/*** For Using Annotation for Bean Specification  **/
		AnnotationConfigWebApplicationContext appContext = 
				new AnnotationConfigWebApplicationContext();
		appContext.register(SpringMvcConfiguration.class);
		appContext.setServletContext(servletContext);
		/**/
		
		ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", 
				new DispatcherServlet(appContext));
		
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");

	}
	
}
