package com.freeman.partyplanner.Model;

import jakarta.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class Item {
    private String name;
    private float price;
    private int bringer;
    private boolean taken;
    private UUID itemId;  // Change itemId to UUID for unique values

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }

    public int getBringer() { return bringer; }
    public void setBringer(int bringer) { this.bringer = bringer; }

    public boolean isTaken() { return taken; }
    public void setTaken(boolean taken) { this.taken = taken; }
}
