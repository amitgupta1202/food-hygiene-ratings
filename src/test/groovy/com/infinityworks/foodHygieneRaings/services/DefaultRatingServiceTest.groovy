package com.infinityworks.foodHygieneRaings.services

import spock.lang.Specification


class DefaultRatingServiceTest extends Specification {

    def "should return at 2 ratings when region is Scotland"() {
        expect:
        new RatingService.DefaultRatingService().getMinimumRatingSet("Scotland").size() == 2
    }

    def "should return at 6 ratings when region is not Scotland"() {
        expect:
        new RatingService.DefaultRatingService().getMinimumRatingSet("UK").size() == 6
    }
}
