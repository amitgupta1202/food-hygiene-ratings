package com.infinityworks.foodHygieneRaings.resources

import org.glassfish.jersey.server.ResourceConfig
import org.glassfish.jersey.test.JerseyTest
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.DefaultListableBeanFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.support.GenericApplicationContext
import spock.lang.Specification

/**
 * adaptor abstract class to enable us write jersey tests in spock
 * this allow to test resources in isolation
 */
abstract class JerseySpec extends Specification {

    private ApplicationContext appCtx

    @Delegate private JerseyTest jerseyTest
    @Delegate private ConfigurableListableBeanFactory factory

    @SuppressWarnings("GroovyUnusedAssignment")
    def setup() {
        factory = new DefaultListableBeanFactory()
        appCtx = new GenericApplicationContext(factory)
        def application = config()
        appCtx.refresh()
        jerseyTest = new JerseyTest(application.property("contextConfig", appCtx)) {}
        jerseyTest.setUp()
    }

    def cleanup() {
        jerseyTest.tearDown()
    }

    abstract ResourceConfig config()
}
