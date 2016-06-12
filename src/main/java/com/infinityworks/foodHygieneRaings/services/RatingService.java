package com.infinityworks.foodHygieneRaings.services;

import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Rating service
 */
public interface RatingService {

    Set<String> getMinimumRatingSet(String regionName);

    @Service
    class DefaultRatingService implements RatingService {

        @Override
        public Set<String> getMinimumRatingSet(String regionName) {
            return "Scotland".equals(regionName) ? ImmutableSet.of("Pass", "Improvement Required") : ImmutableSet.of("1", "2", "3", "4", "5", "Exempt");
        }
    }
}
