package com.infinityworks.foodHygieneRaings.services;

import com.infinityworks.foodHygieneRaings.entities.Authority;
import com.infinityworks.foodHygieneRaings.entities.Establishment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.*;

@Service
class RatingDistributionServiceImpl implements RatingDistributionService {

    private final AuthorityService authorityService;
    private final EstablishmentService establishmentService;
    private final RatingService ratingService;

    @Autowired
    public RatingDistributionServiceImpl(AuthorityService authorityService, EstablishmentService establishmentService, RatingService ratingService) {
        this.authorityService = authorityService;
        this.establishmentService = establishmentService;
        this.ratingService = ratingService;
    }

    @Override
    public Map<String, Long> getRatingDistributionByLocalAuthority(int authorityId) {
        /**
         * using long instead of double, assuming accuracy is not an issue, requirement doesn't suggest we should be using decimal places.
         * For accuracy use Double instead of Long and it can be rounded up to whatever decimal place desired
         */
        CompletableFuture<List<Establishment>> establishmentsFuture = CompletableFuture.supplyAsync(() -> establishmentService.getEstablishmentsByAuthorityId(authorityId));
        CompletableFuture<Optional<Authority>> authorityOptionalFuture = CompletableFuture.supplyAsync(() -> authorityService.getAuthority(authorityId));
        Optional<Authority> authorityOptional = authorityOptionalFuture.join();
        if (!authorityOptional.isPresent()) {
            return Collections.emptyMap();
        }

        List<Establishment> establishments = establishmentsFuture.join();
        Map<String, Long> result = establishments.stream()
                .collect(groupingBy(Establishment::getRatingValue, counting())) //Ideally it should be rating key
                .entrySet().stream()
                .collect(toMap(Map.Entry::getKey, entry -> 100 * entry.getValue() / establishments.size()));
        return mergeMissingRatings(authorityOptional.get(), result);
    }

    private Map<String, Long> mergeMissingRatings(Authority authority, Map<String, Long> ratings) {
        for (String rating : ratingService.getMinimumRatingSet(authority.getRegion())) {
            ratings.computeIfAbsent(rating, absentRating -> 0L);
        }
        return ratings;
    }
}
