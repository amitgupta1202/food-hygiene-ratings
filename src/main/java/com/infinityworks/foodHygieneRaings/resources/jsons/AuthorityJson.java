package com.infinityworks.foodHygieneRaings.resources.jsons;

import com.infinityworks.foodHygieneRaings.entities.Authority;

import java.util.HashMap;
import java.util.Map;

public class AuthorityJson extends Authority {

    private final Map<String, Link> _link = new HashMap<>();

    public AuthorityJson(Authority authority) {
        super(authority.getId(), authority.getCode(), authority.getName(), authority.getRegion());
        addSelfLink();
    }

    public Map<String, Link> get_link() {
        return _link;
    }

    private void addSelfLink() {
        _link.put("self", new Link("authorities/" + this.getId()));
    }

    public AuthorityJson addRatingDistributionLink() {
        _link.put("rating-distribution", new Link("authorities/" + this.getId() + "/rating-distribution"));
        return this;
    }
}
