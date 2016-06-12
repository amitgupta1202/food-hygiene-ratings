package com.infinityworks.foodHygieneRaings.services

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.infinityworks.foodHygieneRaings.entities.Authority
import spock.lang.Specification

@SuppressWarnings("GroovyAssignabilityCheck")
class AuthorityServiceImplTest extends Specification {

    private FoodHygieneRatingHelper foodHygieneRatingHelper
    private AuthorityService authorityService

    def setup() {
        foodHygieneRatingHelper = Mock(FoodHygieneRatingHelper)
        authorityService = new AuthorityServiceImpl(foodHygieneRatingHelper)
        0 * _ //strict mocking
    }

    def newAuthority(int localAuthorityId, String localAuthorityIdCode, String name, String regionName) {
        def authority = new JsonObject()
        authority.addProperty('LocalAuthorityId', localAuthorityId)
        authority.addProperty('LocalAuthorityIdCode', localAuthorityIdCode)
        authority.addProperty('Name', name)
        authority.addProperty('RegionName', regionName)
        return authority
    }

    def newAuthorityList(JsonObject... authorities) {
        def authoritiesJsonArray = new JsonArray()
        for (JsonObject authority : authorities) {
            authoritiesJsonArray.add(authority)
        }
        def expectedResponseJsonObject = new JsonObject()
        expectedResponseJsonObject.add('authorities', authoritiesJsonArray)
        return expectedResponseJsonObject
    }


    def "getAuthorities: should return all the authorities"() {
        given:
        def expectedAuthorities = newAuthorityList(newAuthority(1, 'A1', 'A', 'R1'), newAuthority(2, 'A2', 'A', 'R2'))

        when:
        def result = authorityService.getAuthorities()

        then:
        1 * foodHygieneRatingHelper.tryGetAsJsonObject('Authorities', _ as String) >> Optional.of(expectedAuthorities)

        result.size() == 2
        result[0] == new Authority(1, 'A1', 'A', 'R1')
        result[1] == new Authority(2, 'A2', 'A', 'R2')
    }


    def "getAuthority: should return authority by id"() {
        given:
        def expectedAuthority = newAuthority(1, 'A1', 'A', 'R1')

        when:
        def result = authorityService.getAuthority(1)

        then:
        1 * foodHygieneRatingHelper.tryGetAsJsonObject('Authorities/1', _ as String) >> Optional.of(expectedAuthority)

        result.get() == new Authority(1, 'A1', 'A', 'R1')
    }
}
