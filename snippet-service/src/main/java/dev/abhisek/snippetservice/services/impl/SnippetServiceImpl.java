package dev.abhisek.snippetservice.services.impl;

import dev.abhisek.snippetservice.entity.Snippet;
import dev.abhisek.snippetservice.exception.SnippetNotFoundException;
import dev.abhisek.snippetservice.exception.UserNotFoundException;
import dev.abhisek.snippetservice.repository.SnippetRepository;
import dev.abhisek.snippetservice.services.SnippetService;
import dev.abhisek.snippetservice.services.external.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SnippetServiceImpl implements SnippetService {
    private final SnippetRepository repository;
    private final UserServices userServices;

    @Override
    public Snippet addSnippet(Snippet snippet) {
        if (!userServices.verifyUser(snippet.getUserId()))
            throw new UserNotFoundException();
        snippet.setSnippetId(UUID
                .randomUUID()
                .toString());
        snippet = repository.insert(snippet);
        return snippet;
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
                        .getSnippetId())
                .orElseThrow(SnippetNotFoundException::new);
        newSnippet.setTitle(snippet.getTitle());
        newSnippet.setDescription(snippet.getDescription());
        newSnippet.setCode(snippet.getCode());

        return repository.save(newSnippet);
    }
}
