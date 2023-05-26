package dev.abhisek.userservice.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "users")
public class User {
    @JsonIgnore
    private ObjectId objectId;
    @Id
    private String userId;
    private String name;
    private String email;
    private String password;
    private List<String> roles;

    @JsonIgnore
    @DocumentReference(collection = "snippets")
    private List<Snippet> snippets;
}
