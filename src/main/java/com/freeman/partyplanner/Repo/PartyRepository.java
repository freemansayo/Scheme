package com.freeman.partyplanner.Repo;

import com.freeman.partyplanner.Model.Party;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PartyRepository extends CrudRepository<Party, UUID> {



}
