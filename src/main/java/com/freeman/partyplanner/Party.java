package com.freeman.partyplanner;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class    Party {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String location;
    private Long date;
    private LatLng cords;
    @ElementCollection
    private List<String> members;
//    @ElementCollection
//    private List<String> items;

    public LatLng getCords() {
        return cords;
    }

//    public List<String> getItems() {
//        return items;
//    }
//
//    public void setItems(List<String> items) {
//        this.items = items;
//    }

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

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
