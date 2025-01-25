package com.freeman.partyplanner;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PartyRepository extends CrudRepository<Party, UUID> {



}
