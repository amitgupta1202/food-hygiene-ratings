package com.infinityworks.foodHygieneRaings.services;

import com.infinityworks.foodHygieneRaings.entities.Establishment;

import java.util.List;

public interface EstablishmentService {

    List<Establishment> getEstablishmentsByAuthorityId(int authorityId);
}
