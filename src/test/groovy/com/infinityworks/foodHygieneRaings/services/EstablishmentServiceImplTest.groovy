package com.infinityworks.foodHygieneRaings.services

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.infinityworks.foodHygieneRaings.entities.Establishment
import spock.lang.Specification

@SuppressWarnings("GroovyAssignabilityCheck")
class EstablishmentServiceImplTest extends Specification {

    private EstablishmentService establishmentService
    private FoodHygieneRatingHelper foodHygieneRatingHelper

    def setup() {
        foodHygieneRatingHelper = Mock(FoodHygieneRatingHelper)
        establishmentService = new EstablishmentServiceImpl(foodHygieneRatingHelper)
        0 * _ //strict mocking
    }


    def newEstablishment(int id, String name, String authorityCode, String ratingKey, String ratingValue) {
        def establishment = new JsonObject()
        establishment.addProperty('FHRSID', id)
        establishment.addProperty('BusinessName', name)
        establishment.addProperty('LocalAuthorityCode', authorityCode)
        establishment.addProperty('RatingKey', ratingKey)
        establishment.addProperty('RatingValue', ratingValue)
        return establishment
    }


    def newEstablishmentList(JsonObject... authorities) {
        def establishmentJsonArray = new JsonArray()
        for (JsonObject authority : authorities) {
            establishmentJsonArray.add(authority)
        }
        def expectedResponseJsonObject = new JsonObject()
        expectedResponseJsonObject.add('establishments', establishmentJsonArray)
        return expectedResponseJsonObject
    }


    def "getEstablishmentsByAuthorityId: should return establishment by authority id"() {
        given:
        def expectedEstablishments = newEstablishmentList(newEstablishment(1, 'E1', 'AC', 'RK', 'RV'), newEstablishment(2, 'E2', 'AC', 'RK', 'RV'))

        when:
        def result = establishmentService.getEstablishmentsByAuthorityId(1)

        then:
        1 * foodHygieneRatingHelper.tryGetAsJsonObject("Establishments?localAuthorityId=1", _ as String) >> Optional.of(expectedEstablishments)

        result.size() == 2
        result[0] == new Establishment(1, 'E1', 'AC', 'RK', 'RV')
        result[1] == new Establishment(2, 'E2', 'AC', 'RK', 'RV')
    }
}
