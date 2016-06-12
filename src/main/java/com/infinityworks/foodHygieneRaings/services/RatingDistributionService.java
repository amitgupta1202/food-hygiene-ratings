package com.infinityworks.foodHygieneRaings.services;


import java.util.Map;

public interface RatingDistributionService {

    Map<String, Long> getRatingDistributionByLocalAuthority(int localAuthorityId);
}
