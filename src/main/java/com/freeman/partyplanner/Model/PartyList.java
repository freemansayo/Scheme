package com.freeman.partyplanner.Model;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class PartyList {
    private String partyName;
    private int rsvp;
    private float debt;
    private UUID idOfParty;


    // Getters and setters
    public PartyList(String partyName, int rsvp, float debt, UUID idOfParty) {
        this.partyName = partyName;
        this.rsvp = rsvp;
        this.debt = debt;
        this.idOfParty = idOfParty;
    }

    public PartyList() {

    }

    public String getPartyName() { return partyName; }

    public void setPartyName(String partyName) { this.partyName = partyName; }

    public int getRsvp() { return rsvp; }

    public void setRsvp(int rsvp) { this.rsvp = rsvp; }

    public float getDebt() { return debt; }

    public void setDebt(float debt) { this.debt = debt; }

    public UUID getIdOfParty() { return idOfParty; }

    public void setIdOfParty(UUID idOfParty) { this.idOfParty = idOfParty; }
}
