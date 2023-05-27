package dev.abhisek.snippetservice.repository;

import dev.abhisek.snippetservice.entity.Snippet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SnippetRepository extends MongoRepository<Snippet, String> {
    List<Snippet> findAllByUserId(String userId);
}
