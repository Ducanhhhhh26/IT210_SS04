package com.restaurant.ss04005.Config;

import org.jspecify.annotations.Nullable;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?> @Nullable [] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?> @Nullable [] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
