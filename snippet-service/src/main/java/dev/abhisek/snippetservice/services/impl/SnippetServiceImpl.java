package dev.abhisek.snippetservice.services.impl;

import dev.abhisek.snippetservice.entity.Snippet;
import dev.abhisek.snippetservice.entity.User;
import dev.abhisek.snippetservice.exception.SnippetNotFoundException;
import dev.abhisek.snippetservice.repository.SnippetRepository;
import dev.abhisek.snippetservice.services.SnippetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SnippetServiceImpl implements SnippetService {
    private final SnippetRepository repository;
    private final MongoTemplate mongoTemplate;


    @Override
    public void addSnippet(Snippet snippet) {
        snippet.setSnippetId(UUID
                .randomUUID()
                .toString());
        snippet = repository.insert(snippet);
        mongoTemplate.update(User.class)
                .matching(Criteria.where("userId").is(snippet.getUserId()))
                .apply(new Update().push("snippets", snippet.getSnippetId()))
                .first();
    }

    @Override
    public void deleteSnippet(String snippetId) {
        Snippet snippet = repository.findById(snippetId)
                .orElseThrow();
        repository.delete(snippet);
    }

    @Override
    public Snippet getSnippetBySnippetId(String snippetId) {
        return repository.findById(snippetId).orElseThrow(SnippetNotFoundException::new);
    }

    @Override
    public List<Snippet> getAllSnippetByUserId(String userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public List<Snippet> getAllSnippet() {
        return repository
                .findAll();
    }

    @Override
    public Snippet updateSnippet(Snippet snippet) {
        Snippet newSnippet = repository.findById(snippet
                .getSnippetId()).orElseThrow(SnippetNotFoundException::new);
        newSnippet.setTitle(snippet.getTitle());
        newSnippet.setDescription(snippet.getDescription());
        newSnippet.setCode(snippet.getCode());

        return repository.save(newSnippet);
    }
}
