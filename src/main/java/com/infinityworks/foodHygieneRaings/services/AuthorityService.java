package com.infinityworks.foodHygieneRaings.services;

import com.infinityworks.foodHygieneRaings.entities.Authority;

import java.util.List;
import java.util.Optional;

public interface AuthorityService {

    List<Authority> getAuthorities();

    Optional<Authority> getAuthority(int id);
}
