package dev.abhisek.snippetservice.exception;

import java.util.NoSuchElementException;

public class SnippetNotFoundException extends NoSuchElementException {
    public SnippetNotFoundException() {
        super("Requested snippet not found in server.");
    }

    public SnippetNotFoundException(String message) {
        super(message);
    }
}
