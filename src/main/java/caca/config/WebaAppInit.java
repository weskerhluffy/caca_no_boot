package caca.config;

import javax.servlet.FilterRegistration.Dynamic;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.FilterChainProxy;
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

		// XXX: https://www.logicbig.com/tutorials/spring-framework/spring-web-mvc/delegating-filter-proxy.html
		// XXX: https://stackoverflow.com/questions/32592931/how-to-use-delegatingfilterproxy-in-java-config
		// XXX: https://dzone.com/articles/what-does-spring-delegatingfilterproxy-do
		// XXX: https://springbootdev.com/2017/09/09/spring-security-delegatingfilterproxy/
		DelegatingFilterProxy filterProxy = new DelegatingFilterProxy(
				AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME, rootContext);
		Dynamic registration = container.addFilter(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME,
				filterProxy);
		registration.setAsyncSupported(true);
		registration.addMappingForUrlPatterns(
				EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR, DispatcherType.ASYNC), true, "/*");

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
