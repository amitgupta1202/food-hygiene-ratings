package com.infinityworks.foodHygieneRaings.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

/**
 * Routines for jersey response, encapsulates the common routines
 *
 * visibility is package level and should not be increased
 */
@Component
class FoodHygieneRatingHelper {

    private final static Logger LOGGER = LoggerFactory.getLogger(FoodHygieneRatingHelper.class);

    private final String foodHygieneBaseUri;

    @Autowired
    public FoodHygieneRatingHelper(@Value("${foodHygieneRating.baseUrl}") String foodHygieneBaseUri) {
        this.foodHygieneBaseUri = foodHygieneBaseUri;
    }

    Optional<JsonObject> tryGetAsJsonObject(String relativeUri, String errorMessage) {
        Response response = buildInvocation(foodHygieneBaseUri + relativeUri).get();
        if (response.getStatus() == 200) {
            return Optional.of(convertToJsonObject(response).getAsJsonObject());
        } else {
            LOGGER.error(errorMessage + " Response Status: " + response.getStatus());
        }
        return Optional.empty();
    }

    private static Invocation.Builder buildInvocation(String uri) {
        return ClientBuilder.newClient().target(uri)
                .request(APPLICATION_JSON_TYPE)
                .header("x-api-version", 2)
                .header("Accept-Language", "cy-GB");
    }

    private static JsonObject convertToJsonObject(Response response) {
        BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream) response.getEntity(), StandardCharsets.UTF_8));
        return new Gson().fromJson(reader, JsonObject.class);
    }
}
