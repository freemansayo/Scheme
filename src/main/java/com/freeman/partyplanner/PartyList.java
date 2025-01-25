package com.freeman.partyplanner;

import jakarta.persistence.Embeddable;

@Embeddable
public class PartyList {
    private String partyName;
    private int rsvp;
    private float debt;


    // Getters and setters
    public String getPartyName() { return partyName; }

    public void setPartyName(String partyName) { this.partyName = partyName; }

    public int getRsvp() { return rsvp; }

    public void setRsvp(int rsvp) { this.rsvp = rsvp; }

    public float getDebt() { return debt; }

    public void setDebt(float debt) { this.debt = debt; }
}
