package com.infinityworks.foodHygieneRaings.services

import com.infinityworks.foodHygieneRaings.entities.Authority
import com.infinityworks.foodHygieneRaings.entities.Establishment
import spock.lang.Specification


@SuppressWarnings("GroovyAssignabilityCheck")
class RatingDistributionServiceImplTest extends Specification {

    private RatingDistributionService ratingDistributionService
    private AuthorityService authorityService
    private EstablishmentService establishmentService
    private RatingService ratingService


    def setup() {
        authorityService = Mock(AuthorityService)
        establishmentService = Mock(EstablishmentService)
        ratingService = Mock(RatingService)

        ratingDistributionService = new RatingDistributionServiceImpl(authorityService, establishmentService, ratingService)
        0 * _ //strict mocking
    }


    def """getRatingDistributionByLocalAuthority:
            should return rating distribution by local authority and include all the ratings included in minimum set
            and also include ratings which are not in minimum set but returned by establishment service"""() {
        given:
        def minimumRatingSetForUK = ["1", "2", "3", "4", "5", "Exempt"]

        def authority = new Authority(1, 'code1', 'authorityName1', 'UK')
        1 * authorityService.getAuthority(1) >> Optional.of(authority)

        def establishmentA = new Establishment(2, 'establishmentA', 'code1', 'ratingKey-5', '5')
        def establishmentB = new Establishment(3, 'establishmentB', 'code1', 'ratingKey-5', '5')
        def establishmentC = new Establishment(4, 'establishmentC', 'code1', 'ratingKey-1', '1')
        def establishmentD = new Establishment(5, 'establishmentD', 'code1', 'ratingKey-Exempt', 'Exempt')
        def establishmentE = new Establishment(6, 'establishmentE', 'code1', 'ratingKey-Unknown', 'Unknown')
        1 * establishmentService.getEstablishmentsByAuthorityId(1) >> [establishmentA, establishmentB, establishmentC, establishmentD, establishmentE]

        when:
        def result = ratingDistributionService.getRatingDistributionByLocalAuthority(1)

        then:
        1 * ratingService.getMinimumRatingSet('UK') >> minimumRatingSetForUK

        result.size() == 7
        result."5" == 40L
        result."4" == 0L
        result."3" == 0L
        result."2" == 0L
        result."1" == 20L
        result."Exempt" == 20L
        result."Unknown" == 20L
    }
}
