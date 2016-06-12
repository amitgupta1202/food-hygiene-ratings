package com.infinityworks.foodHygieneRaings.resources.jsons;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * contains link of authorities
 */
public class AuthoritiesJson {

    private final List<AuthorityJson> authorities;
    private final Map<String, Link> _link = new HashMap<>();

    public AuthoritiesJson(List<AuthorityJson> authorities) {
        this.authorities = authorities;
        addSelfLink();
    }

    public List<AuthorityJson> getAuthorities() {
        return authorities;
    }

    public Map<String, Link> get_link() {
        return _link;
    }

    private void addSelfLink() {
        _link.put("self", new Link("authorities"));
    }
}
