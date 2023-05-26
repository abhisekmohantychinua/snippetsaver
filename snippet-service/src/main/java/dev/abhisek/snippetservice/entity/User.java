package dev.abhisek.snippetservice.entity;

import lombok.Data;
import org.bson.types.ObjectId;

import java.util.List;

@Data
public class User {
    private ObjectId _id;
    private String userId;
    private String name;
    private String email;
    private String password;
    private List<String> roles;
}