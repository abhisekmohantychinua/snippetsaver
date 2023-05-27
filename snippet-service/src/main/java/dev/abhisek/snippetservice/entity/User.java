package dev.abhisek.snippetservice.entity;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private String userId;
    private String name;
    private String email;
    private String password;
    private List<String> roles;
    private List<Snippet> snippets;
}
