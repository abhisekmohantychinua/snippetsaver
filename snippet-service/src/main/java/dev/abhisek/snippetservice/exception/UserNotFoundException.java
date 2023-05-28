package dev.abhisek.snippetservice.exception;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

public class UserNotFoundException extends NoSuchElementException implements Supplier<NoSuchElementException> {
    public UserNotFoundException() {
        super("Requested User not found on server !!!");
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    @Override
    public NoSuchElementException get() {
        return new NoSuchElementException("Requested User not found on server");
    }
}
