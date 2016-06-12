package com.infinityworks.foodHygieneRaings;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

/**
 * jersey config
 */
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(RequestContextFilter.class);
        packages("com.infinityworks.foodHygieneRaings.resources");
        register(LoggingFilter.class);
    }
}
