package dev.abhisek.userservice.exception;

import java.util.NoSuchElementException;

public class UserNotFoundException extends NoSuchElementException {

    public UserNotFoundException() {
        super("Requested User not found on server !!!");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
