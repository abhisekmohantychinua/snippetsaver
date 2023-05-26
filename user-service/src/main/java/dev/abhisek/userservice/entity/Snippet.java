package dev.abhisek.userservice.entity;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class Snippet {
    private ObjectId _id;
    private String snippetId;
    private String title;
    private String description;
    private String code;
    private String userId;
}
