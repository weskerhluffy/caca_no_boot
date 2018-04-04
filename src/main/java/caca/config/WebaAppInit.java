package caca.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.context.ContextLoaderListener;

public class WebaAppInit implements WebApplicationInitializer {

	// XXX:
	// https://samerabdelkafi.wordpress.com/2014/08/03/spring-mvc-full-java-based-config/
	private static final Logger logger = LoggerFactory.getLogger(WebaAppInit.class);

	public void onStartup(ServletContext container) {
		logger.error("todos lo nombran");
		// Create the 'root' Spring application context
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		// rootContext.register(ServiceConfig.class, JPAConfig.class,
		// SecurityConfig.class);
		rootContext.register(JPutoConfig.class, SecuCacaConfig.class);

		// Manage the lifecycle of the root application context
		container.addListener(new ContextLoaderListener(rootContext));
		DelegatingFilterProxy filterProxy = new DelegatingFilterProxy();
		filterProxy.setTargetBeanName(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME);
		container.addFilter("myFilter", filterProxy).addMappingForUrlPatterns(null, true, "/*");

		// Create the dispatcher servlet's Spring application context
		AnnotationConfigWebApplicationContext dispatcherServlet = new AnnotationConfigWebApplicationContext();
		dispatcherServlet.register(MvcConfig.class);

		// Register and map the dispatcher servlet
		ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher",
				new DispatcherServlet(dispatcherServlet));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");

	}
}
