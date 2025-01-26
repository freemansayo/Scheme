package com.freeman.partyplanner.Model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int user_id;
    private String username;
    private String password;

    @ElementCollection
    @CollectionTable(name = "user_parties", joinColumns = @JoinColumn(name = "user_id"))
    private List<PartyList> my_parties;

    @ElementCollection
    private List<Integer> friends;

    // Getters and setters
    public int getUser_id() { return user_id; }

    public void setUser_id(int user_id) { this.user_id = user_id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public List<PartyList> getMy_parties() {
        return my_parties;
    }

    public void setMy_parties(List<PartyList> my_parties) {
        this.my_parties = my_parties;
    }

    public List<Integer> getFriends() { return friends; }

    public void setFriends(List<Integer> friends) { this.friends = friends; }

}
