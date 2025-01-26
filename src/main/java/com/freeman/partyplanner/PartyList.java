package com.freeman.partyplanner;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class PartyList {
    private String partyName;
    private int rsvp;
    private float debt;
    private UUID idOfParty;


    // Getters and setters
    public String getPartyName() { return partyName; }

    public void setPartyName(String partyName) { this.partyName = partyName; }

    public int getRsvp() { return rsvp; }

    public void setRsvp(int rsvp) { this.rsvp = rsvp; }

    public float getDebt() { return debt; }

    public void setDebt(float debt) { this.debt = debt; }

    public UUID getIdOfParty() { return idOfParty; }

    public void setIdOfParty(UUID idOfParty) { this.idOfParty = idOfParty; }
}
