package com.freeman.partyplanner.Controller;

import com.freeman.partyplanner.Model.Item;
import com.freeman.partyplanner.Model.Party;
import com.freeman.partyplanner.Model.User;
import com.freeman.partyplanner.Model.PartyList;
import com.freeman.partyplanner.Repo.PartyRepository;
import com.freeman.partyplanner.Repo.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.stream.Collectors;

@RestController("/")
public class FrontController {
    private final PartyRepository partyRepository;
    private final UserRepository userRepository;

    @Autowired
    public FrontController(PartyRepository partyRepository, UserRepository userRepository) {
        this.partyRepository = partyRepository;
        this.userRepository = userRepository;
    }

    // sign up
    @PostMapping(value = "/users")
    public Integer createUser(@RequestBody User user) {
        User createdUser = userRepository.save(user);
        return createdUser.getUser_id();
    }

    // login
    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        Optional<User> user = userRepository.findByUsernameAndPassword(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    // technically security risk but wtvr for now
    // getting user info from database by id
    @GetMapping(value = "/users/{userId}")
    public Optional<User> getUser(@PathVariable Integer userId) {
        return userRepository.findById(userId);
    }

    // delete user by id
    // SECURITY ISSUE, must make sure it's the owner of the account that's deleting
    @DeleteMapping(value = "/users/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        userRepository.deleteById(userId);
    }

    // getting user's friends list
    @GetMapping("/{userId}/friends")
    public ResponseEntity<?> getUserFriends(@PathVariable Integer userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            List<Integer> friends = user.get().getFriends();
            return ResponseEntity.ok(friends);
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    // adding to user's friends list
    @PutMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<?> addUserFriend(@PathVariable Integer userId, @PathVariable Integer friendId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<User> optionalFriend = userRepository.findById(friendId);

        if (optionalUser.isPresent() && optionalFriend.isPresent()) {
            User user = optionalUser.get();
            user.getFriends().add(friendId);
            userRepository.save(user);

            User friend = optionalFriend.get();
            friend.getFriends().add(userId);
            userRepository.save(friend);

            return ResponseEntity.ok("User friended successfully");
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    // getting user's party list
    @GetMapping("/{userId}/parties")
    public ResponseEntity<?> getUserParties(@PathVariable Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOptional.get();

        // Extract party IDs from the user's PartyList
        List<UUID> partyIds = user.getMy_parties().stream()
                .map(PartyList::getIdOfParty)
                .collect(Collectors.toList());

        if (partyIds.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        // Fetch all parties in a single query using partyRepository
        List<Party> parties = (List<Party>) partyRepository.findAllById(partyIds);

        return ResponseEntity.ok(parties);
    }

    // creating a party and also updating user table to include new party
    @PostMapping(value = "/parties/{userId}")
    @Transactional  // If one repo function fails, rewinds both so nothing is added by mistake
    public UUID createParty(@RequestBody Party party, @PathVariable Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Save the party and retrieve the ID
        Party createdParty = partyRepository.save(party);
        UUID partyId = createdParty.getId();

        // Add the new party to the user's party list
        user.getMy_parties().add(new PartyList(createdParty.getNameOfParty(), 0, 0, partyId));

        // Save the updated user (cascade if mapped correctly)
        userRepository.save(user);

        return partyId;
    }

    // getting party info by party id
    @GetMapping("/parties/{partyId}")
    public Optional<Party> getParty(@PathVariable String partyId){
        return partyRepository.findById(UUID.fromString(partyId));
    }

    // joining a party
    @PutMapping(value = "/{userId}/join/{partyId}")
    public ResponseEntity<?> joinParty(@PathVariable Integer userId, @PathVariable UUID partyId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Party> partyOptional = partyRepository.findById(partyId);

        if (userOptional.isPresent() && partyOptional.isPresent()) {
            User user = userOptional.get();
            Party party = partyOptional.get();

            List<PartyList> userPartyLists = user.getMy_parties();

            // Check if the party ID is already in the user's list
            for (PartyList partyList : userPartyLists) {
                if (partyList.getIdOfParty().equals(partyId)) {
                    return ResponseEntity.badRequest().body("User is already a member of this party");
                }
            }

            user.getMy_parties().add(new PartyList(party.getNameOfParty(), 0, 0, partyId));
            userRepository.save(user);

            return ResponseEntity.ok("User " + userId + " has joined the party " + partyId);
        } else {
            return ResponseEntity.status(404).body("User or Party not found");
        }
    }

    // delete a party by id, NEED TO IMPLEMENT CHECKING THAT USER IS OWNER AND IS THE LOGGED-IN USER
    @DeleteMapping("/parties/{partyId}")
    public void deleteParty(@PathVariable UUID partyId){
        partyRepository.deleteById(partyId);

        // still need to delete from user side

    }

    @GetMapping("/item/{partyId}")
    public List<Item> getItems(@PathVariable String partyId){
        Optional<Party> optionalParty = partyRepository.findById(UUID.fromString(partyId));
        List<Item> items = new ArrayList<>();
        if (optionalParty.isPresent()) {
            Party party = optionalParty.get();
            return party.getItem();
        }

        return null;
    }

    @PostMapping(value = "/{partyId}/item")
    public ResponseEntity<?> addItemToParty(@PathVariable UUID partyId, @RequestBody Item item) {
        Optional<Party> partyOptional = partyRepository.findById(partyId);
        if (partyOptional.isPresent()) {
            Party party = partyOptional.get();

            party.getItem().add(item);
            partyRepository.save(party);

            return ResponseEntity.ok("Item added successfully to party: " + partyId);
        } else {
            return ResponseEntity.status(404).body("Party not found");
        }

    }

    @DeleteMapping(value = "/{partyId}/item")
    public void deleteItemFromParty(@PathVariable UUID partyId, @RequestBody Item item) {
        Optional<Party> partyOptional = partyRepository.findById(partyId);
        if (partyOptional.isPresent()) {
            Party party = partyOptional.get();

            boolean removed = party.getItem().removeIf(existingItem ->
                    existingItem.getName().equals(item.getName()) &&
                            existingItem.getPrice() == item.getPrice() &&
                            existingItem.getBringer() == item.getBringer() &&
                            existingItem.isTaken() == item.isTaken()
            );

        }
    }

    //@PostMapping(value = "/{userId}/itemBringer")


//    @GetMapping("/parties")
//    public Collection<Party> getParties(){
//        List<Party> parties = new ArrayList<>();
//        partyRepository.findAll().forEach(parties::add);
//        return parties;
//    }

//    @GetMapping(value = "/users")
//    public List<User> getUsers() {
//        List<User> users = new ArrayList<>();
//        userRepository.findAll().forEach(users::add);
//        return users;
//    }

//    @GetMapping("/{userId}/party-names")
//    public ResponseEntity<?> getUserPartyNames(@PathVariable Integer userId) {
//        Optional<User> user = userRepository.findById(userId);
//
//        if (user.isPresent()) {
//            List<String> partyNames = user.get().getMy_parties()
//                    .stream()
//                    .map(PartyList::getPartyName)
//                    .collect(Collectors.toList());
//
//            return ResponseEntity.ok(partyNames);
//        } else {
//            return ResponseEntity.status(404).body("User not found");
//        }
//    }
}
