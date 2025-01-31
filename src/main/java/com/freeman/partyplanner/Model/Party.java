package com.freeman.partyplanner.Model;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String location;
    private String date;
    private LatLng cords;
    private String nameOfParty;
    @ElementCollection
    private List<Chat> chat;
    @ElementCollection
    private List<Item> item;
//    @ElementCollection
//    private List<String> items;


    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

    public String getNameOfParty() { return nameOfParty; }
    public void setNameOfParty(String nameOfParty) { this.nameOfParty = nameOfParty; }

    public LatLng getCords() {
        return cords;
    }
    public List<Chat> getChat() {
        return chat;
    }

    public void setChat(List<Chat> chat) {
        this.chat = chat;
    }



    public void setCords(LatLng cords) {
        this.cords = cords;
    }

    public Party() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
