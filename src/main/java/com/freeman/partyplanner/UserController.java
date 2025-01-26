package com.freeman.partyplanner;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.stream.Collectors;

import java.util.*;

@RestController
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping(value = "/users")
    public Integer createUser(@RequestBody User user) {
        User createdUser = userRepository.save(user);
        return createdUser.getUser_id();
    }

    @GetMapping(value = "/users")
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @GetMapping(value = "/users/{userId}")
    public Optional<User> getUser(@PathVariable Integer userId) {
        return userRepository.findById(userId);
    }

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

    @DeleteMapping(value = "/users/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        userRepository.deleteById(userId);
    }

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

    @GetMapping("/{userId}/party-names")
    public ResponseEntity<?> getUserPartyNames(@PathVariable Integer userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            List<String> partyNames = user.get().getMy_parties()
                    .stream()
                    .map(PartyList::getPartyName)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(partyNames);
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }


}
