package com.infinityworks.foodHygieneRaings.resources;

import com.infinityworks.foodHygieneRaings.entities.Authority;
import com.infinityworks.foodHygieneRaings.resources.jsons.AuthoritiesJson;
import com.infinityworks.foodHygieneRaings.resources.jsons.AuthorityJson;
import com.infinityworks.foodHygieneRaings.services.AuthorityService;
import com.infinityworks.foodHygieneRaings.services.RatingDistributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.apache.commons.lang3.math.NumberUtils.isNumber;

@Path("authorities")
@Produces(APPLICATION_JSON)
@Component
public class AuthorityResource {

    private final AuthorityService authorityService;
    private final RatingDistributionService ratingDistributionService;

    @Autowired
    public AuthorityResource(AuthorityService authorityService, RatingDistributionService ratingDistributionService) {
        this.authorityService = authorityService;
        this.ratingDistributionService = ratingDistributionService;
    }

    @GET
    public AuthoritiesJson getAll() {
        return new AuthoritiesJson(authorityService.getAuthorities().stream()
                .map(authority -> new AuthorityJson(authority).addRatingDistributionLink())
                .collect(Collectors.toList()));
    }

    @GET
    @Path("{id}")
    public AuthorityJson getAuthority(@PathParam("id") int authorityId) {
        return authorityService.getAuthority(authorityId)
                .map(authority -> new AuthorityJson(authority).addRatingDistributionLink())
                .orElseThrow(() -> new NotFoundException("Authority with id = " + authorityId + " not found."));
    }

    @GET
    @Path("{id}/rating-distribution")
    public Map<String, Long> getRatingDistributionByAuthority(@PathParam("id") int authorityId) {
        return ratingDistributionService.getRatingDistributionByLocalAuthority(authorityId).entrySet().stream()
                .collect(Collectors.toMap(entry -> transformRatingValueToDisplayText(entry.getKey()), Map.Entry::getValue, (l1, l2) -> null, () -> new TreeMap<>(new RatingComparator())));
    }

    /**
     * convering value like 5 to 5-star, 4 to 4-star etc
     */
    private String transformRatingValueToDisplayText(String ratingValue) {
        if (ratingValue.length() == 1 && isNumber(ratingValue)) {
            return ratingValue + "-star";
        }
        return ratingValue;
    }

    /**
     * we want to sort the 5-star, 4-star etc in descending order then others like Exempt, Pass etc in ascending
     */
    private class RatingComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.endsWith("-star") && o2.endsWith("-star") ? o2.compareTo(o1) : o1.compareTo(o2);
        }
    }
}
