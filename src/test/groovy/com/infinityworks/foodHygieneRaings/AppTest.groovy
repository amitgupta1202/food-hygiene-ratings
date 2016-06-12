package com.infinityworks.foodHygieneRaings

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.context.ApplicationContext
import spock.lang.Specification

/**
 * Context load test
 */
@SpringApplicationConfiguration(classes = App.class)
class AppTest extends Specification {

    @Autowired
    ApplicationContext context

    def 'should load context and all dependent beans'() {
        expect:
        context != null
        context.containsBean("authorityResource")
    }
}
