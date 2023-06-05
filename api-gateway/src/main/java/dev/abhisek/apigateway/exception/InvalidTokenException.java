package dev.abhisek.apigateway.exception;

import javax.naming.AuthenticationException;

public class InvalidTokenException extends AuthenticationException {
    public InvalidTokenException(){
        super("Invalid token provided");
    }
}
