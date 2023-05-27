package dev.abhisek.userservice.controller;

import dev.abhisek.userservice.entity.User;
import dev.abhisek.userservice.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserServices services;

    //User controllers

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody User user) {
        services.addUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("""
                        User created successfully.
                        User id will be sent to provided email.
                        """);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserByUserId(@PathVariable String userId) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(services
                        .getUserByUserId(userId));
    }

    @GetMapping("/snippet/{snippetId}")
    public ResponseEntity<User> getUserBySnippetId(@PathVariable String snippetId) {
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(services
                        .getUserBySnippetId(snippetId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserByUserId(@PathVariable String userId) {
        services.deleteUserByUserId(userId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("Deleted Successfully.");
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId
            , @RequestBody User user) {

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(services
                        .updateUser(userId, user));
    }

    //Admin controllers

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity
                .ok(services
                        .getAllUsers());
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestBody User user) {
        services.deleteUser(user);
        return ResponseEntity
                .ok("Deleted successfully");
    }

}
