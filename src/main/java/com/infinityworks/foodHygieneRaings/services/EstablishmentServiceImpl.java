package com.infinityworks.foodHygieneRaings.services;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.infinityworks.foodHygieneRaings.entities.Establishment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
class EstablishmentServiceImpl implements EstablishmentService {

    private final FoodHygieneRatingHelper foodHygieneRatingHelper;

    @Autowired
    EstablishmentServiceImpl(FoodHygieneRatingHelper foodHygieneRatingHelper) {
        this.foodHygieneRatingHelper = foodHygieneRatingHelper;
    }

    @Override
    public List<Establishment> getEstablishmentsByAuthorityId(int authorityId) {
        Optional<JsonObject> jsonObject = foodHygieneRatingHelper.tryGetAsJsonObject("Establishments?localAuthorityId=" + authorityId, "Cannot retrieve establishments.");
        if (jsonObject.isPresent()) {
            Spliterator<JsonElement> establishments = jsonObject.get()
                    .get("establishments").getAsJsonArray()
                    .spliterator();
            return StreamSupport.stream(establishments, false)
                    .map(EstablishmentServiceImpl::toEstablishment)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }


    private static Establishment toEstablishment(JsonElement jsonElement) {
        JsonObject establishmentJson = jsonElement.getAsJsonObject();
        return new Establishment(establishmentJson.get("FHRSID").getAsInt(),
                establishmentJson.get("BusinessName").getAsString(),
                establishmentJson.get("LocalAuthorityCode").getAsString(),
                establishmentJson.get("RatingKey").getAsString(),
                establishmentJson.get("RatingValue").getAsString());
    }
}
