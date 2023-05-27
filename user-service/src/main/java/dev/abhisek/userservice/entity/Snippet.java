package dev.abhisek.userservice.entity;

import lombok.Data;

@Data
public class Snippet {
    private String snippetId;
    private String title;
    private String description;
    private String code;
    private String userId;
}
