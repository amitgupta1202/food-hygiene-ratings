package com.infinityworks.foodHygieneRaings.resources

import com.infinityworks.foodHygieneRaings.JerseyConfig
import com.infinityworks.foodHygieneRaings.entities.Authority
import com.infinityworks.foodHygieneRaings.services.AuthorityService
import com.infinityworks.foodHygieneRaings.services.RatingDistributionService
import org.glassfish.jersey.server.ResourceConfig

import static javax.ws.rs.core.MediaType.APPLICATION_JSON

@SuppressWarnings("GroovyAssignabilityCheck")
class AuthorityResourceTest extends JerseySpec {

    private AuthorityService authorityService = Mock(AuthorityService)
    private RatingDistributionService ratingDistributionService = Mock(RatingDistributionService)

    @Override
    ResourceConfig config() {
        registerSingleton('authorityResource', new AuthorityResource(authorityService, ratingDistributionService))
        return new JerseyConfig()
    }

    def setup() {
        0 * _ //strict mocking
    }

    def "should return all authorities with response status 200"() {
        when:
        def response = target('authorities').request(APPLICATION_JSON).get()

        then:
        1 * authorityService.getAuthorities() >> [new Authority(1, 'C', 'N', 'R')]

        response.status == 200
        response.readEntity(Map) == [authorities: [[id    : 1,
                                                    code  : 'C',
                                                    name  : 'N',
                                                    region: 'R',
                                                    _link : ['rating-distribution': [href: 'authorities/1/rating-distribution'],
                                                             self                 : [href: 'authorities/1']
                                                    ]]],
                                     _link      : [self: [href: 'authorities']]]
    }


    def "should return authority by id with response status 200"() {
        given:
        def authorityId = 1
        def authority = new Authority(1, 'C', 'N', 'R')

        when:
        def response = target("authorities/$authorityId").request(APPLICATION_JSON).get()

        then:
        1 * authorityService.getAuthority(1) >> Optional.of(authority)

        response.status == 200
        response.readEntity(Map) == [id    : 1,
                                     code  : 'C',
                                     name  : 'N',
                                     region: 'R',
                                     _link : ['rating-distribution': [href: 'authorities/1/rating-distribution'],
                                              self                 : [href: 'authorities/1']]]
    }


    def "should return response status as 404 when authority id is not found"() {
        given:
        def authorityId = 1

        when:
        def response = target("authorities/$authorityId").request(APPLICATION_JSON).get()

        then:
        1 * authorityService.getAuthority(1) >> Optional.empty()

        response.status == 404
    }


    def "should return rating distribution for given authority with response status 200"() {
        given:
        def authorityId = 1
        def expectedRatingDistribution = ['5-star': 50L, '4-star': 0L, '3-star': 0L, '2-star': 0L, '1-star': 25L, 'Exempt': 25L]

        when:
        def response = target("authorities/$authorityId/rating-distribution").request(APPLICATION_JSON).get()

        then:
        1 * ratingDistributionService.getRatingDistributionByLocalAuthority(1) >> ['5': 50L, '2': 0L, '4': 0L, '3': 0L, '1': 25L, 'Exempt': 25L]

        response.status == 200
        response.readEntity(Map) == expectedRatingDistribution
    }


    def "should return rating distribution for give authority in custom sorted order"() {
        given: 'set the ratings in random order'
        1 * ratingDistributionService.getRatingDistributionByLocalAuthority(1) >> ['5': 50L, '2': 0L, '4': 0L, '3': 0L, '1': 25L, 'Exempt': 25L]

        when:
        def result = new AuthorityResource(authorityService, ratingDistributionService).getRatingDistributionByAuthority(1).entrySet().toArray()

        then: 'results are returned in well defined order'
        result[0].key == "5-star"
        result[1].key == "4-star"
        result[2].key == "3-star"
        result[3].key == "2-star"
        result[4].key == "1-star"
        result[5].key == "Exempt"
    }


}
