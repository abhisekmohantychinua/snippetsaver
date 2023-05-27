package dev.abhisek.snippetservice.services;

import dev.abhisek.snippetservice.entity.Snippet;

import java.util.List;

public interface SnippetService {
    Snippet addSnippet(Snippet snippet);
    void deleteSnippet(String snippetId);

    Snippet getSnippetBySnippetId(String snippetId);
    List<Snippet> getAllSnippetByUserId(String userId);
    List<Snippet> getAllSnippet();

    Snippet updateSnippet(Snippet snippet);
}
