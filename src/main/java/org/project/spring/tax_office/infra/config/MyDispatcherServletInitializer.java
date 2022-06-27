package org.project.spring.tax_office.infra.config;

import org.project.spring.tax_office.infra.config.app.AppConfig;
import org.project.spring.tax_office.infra.web.listener.LocaleSessionListener;
import org.project.spring.tax_office.infra.web.encoding.EncodingFilter;
import org.project.spring.tax_office.infra.web.security.SecurityFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyDispatcherServletInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
        root.register(AppConfig.class);

        servletContext.addListener(new ContextLoaderListener(root));
        servletContext.addListener(buildLocaleSessionListener());

        FilterRegistration.Dynamic auth = servletContext.addFilter("security", new SecurityFilter());
        auth.addMappingForUrlPatterns(null, false, "/*");

        FilterRegistration.Dynamic encoding = servletContext.addFilter("encoding", new EncodingFilter());
        encoding.addMappingForUrlPatterns(null, false, "/*");

        DispatcherServlet dispatcherServlet = new DispatcherServlet(new GenericWebApplicationContext());
        ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher", dispatcherServlet);

        registration.setLoadOnStartup(1);
        registration.addMapping("/service/*");
    }

    private LocaleSessionListener buildLocaleSessionListener() {
        List<Locale> locales = new ArrayList<>();
        Locale selectedLocale = new Locale("en");
        locales.add(selectedLocale);
        locales.add(new Locale("ru"));
        return new LocaleSessionListener(locales, selectedLocale);
    }
}


