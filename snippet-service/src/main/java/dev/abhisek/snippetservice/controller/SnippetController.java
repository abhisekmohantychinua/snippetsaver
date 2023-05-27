package dev.abhisek.snippetservice.controller;

import dev.abhisek.snippetservice.entity.Snippet;
import dev.abhisek.snippetservice.services.SnippetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/snippet")
public class SnippetController {
    private final SnippetService service;


    //controllers for user
    @PostMapping("/{userId}")
    public ResponseEntity<Snippet> addSnippet(@RequestBody Snippet snippet
            , @PathVariable String userId) {
        snippet.setUserId(userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.addSnippet(snippet));
    }

    // TODO: 27-05-2023 add getAllSnippets()
    @GetMapping("/{snippetId}")
    public ResponseEntity<Snippet> getSnippetBySnippetId(@PathVariable String snippetId) {
        return ResponseEntity
                .ok(service
                        .getSnippetBySnippetId(snippetId));
    }

    @PutMapping("/{snippetId}")
    public ResponseEntity<Snippet> updateSnippet(@PathVariable String snippetId
            , @RequestBody Snippet snippet) {
        snippet.setSnippetId(snippetId);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(service
                        .updateSnippet(snippet));
    }

    @DeleteMapping("/{snippetId}")
    public ResponseEntity<String> deleteSnippet(@PathVariable String snippetId) {
        service.deleteSnippet(snippetId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("Deleted Successfully");
    }

    //controllers for admin and insider service

    @GetMapping("user/{userId}")
    public ResponseEntity<List<Snippet>> getSnippetByUserId(@PathVariable String userId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service
                        .getAllSnippetByUserId(userId));
    }


}
