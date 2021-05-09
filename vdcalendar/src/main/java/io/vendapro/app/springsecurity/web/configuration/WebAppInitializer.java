package io.vendapro.app.springsecurity.web.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import io.vendapro.app.springsecurity.configuration.DataSourceConfig;
import io.vendapro.app.springsecurity.configuration.JavaConfig;
import io.vendapro.app.springsecurity.configuration.SecurityConfig;

/**
 * Replaces web.xml.txt in Servlet v.3.0+
 *
 * @see
 */
public class WebAppInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer
        implements WebApplicationInitializer {

    private static final Logger logger = LoggerFactory
            .getLogger(WebAppInitializer.class);

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { JavaConfig.class, SecurityConfig.class, DataSourceConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebMvcConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" }; // or *.html
    }

    @Override
    public void onStartup(final ServletContext servletContext)
            throws ServletException {

        // Register DispatcherServlet
        super.onStartup(servletContext);

        // Register H2 Admin console:
        ServletRegistration.Dynamic h2WebServlet = servletContext.addServlet("h2WebServlet",
                new org.h2.server.web.WebServlet());
        h2WebServlet.addMapping("/admin/h2/*");
        h2WebServlet.setInitParameter("webAllowOthers", "true");

    }

} // The End...
