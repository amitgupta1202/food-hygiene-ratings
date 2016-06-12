package com.infinityworks.foodHygieneRaings.services;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.infinityworks.foodHygieneRaings.entities.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
class AuthorityServiceImpl implements AuthorityService {

    private final FoodHygieneRatingHelper foodHygieneRatingHelper;

    @Autowired
    AuthorityServiceImpl(FoodHygieneRatingHelper foodHygieneRatingHelper) {
        this.foodHygieneRatingHelper = foodHygieneRatingHelper;
    }

    @Override
    public List<Authority> getAuthorities() {
        Optional<JsonObject> jsonObject = foodHygieneRatingHelper.tryGetAsJsonObject("Authorities", "Cannot retrieve authorities.");
        if (jsonObject.isPresent()) {
            Spliterator<JsonElement> authorities = jsonObject.get()
                    .get("authorities").getAsJsonArray()
                    .spliterator();
            return StreamSupport.stream(authorities, false)
                    .map(AuthorityServiceImpl::toAuthority)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Authority> getAuthority(int authorityId) {
        Preconditions.checkArgument(authorityId > 0, "Invalid authorityId");
        return foodHygieneRatingHelper.tryGetAsJsonObject("Authorities/" + authorityId, "Cannot retrieve authority. Authority Id: " + authorityId)
                .map(AuthorityServiceImpl::toAuthority);
    }

    private static Authority toAuthority(JsonElement jsonElement) {
        JsonObject authorityJson = jsonElement.getAsJsonObject();
        return new Authority(authorityJson.get("LocalAuthorityId").getAsInt(),
                authorityJson.get("LocalAuthorityIdCode").getAsString(),
                authorityJson.get("Name").getAsString(),
                authorityJson.get("RegionName").getAsString());
    }
}
