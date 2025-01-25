package com.freeman.partyplanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.util.*;

@RestController
public class PartyController {
    private final PartyRepository partyRepository;

    @Autowired
    public PartyController(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }

    @PostMapping(value = "/parties")
    public UUID createParty(@RequestBody Party party){
        Party createdParty = partyRepository.save(party);
        return createdParty.getId();
    }
    

    @DeleteMapping("/parties/{partyId}")
    public void deleteParty(@PathVariable UUID partyId){
        partyRepository.deleteById(partyId);
    }

    @GetMapping("/parties/{partyId}")
    public Optional<Party> getParty(@PathVariable String partyId){
        return partyRepository.findById(UUID.fromString(partyId));
    }

    @GetMapping("/parties")
    public Collection<Party> getParties(){
        List<Party> parties= new ArrayList<>();
        partyRepository.findAll().forEach(parties::add);
        return parties;
    }

}
