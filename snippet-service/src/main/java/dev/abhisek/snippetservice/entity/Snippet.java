package dev.abhisek.snippetservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "snippets")
public class Snippet {
    @Id
    private String snippetId;
    private String title;
    private String description;
    private String code;
    private String userId;
}
