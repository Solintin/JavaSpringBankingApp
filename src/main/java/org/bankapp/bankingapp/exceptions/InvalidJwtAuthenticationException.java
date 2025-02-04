package org.bankapp.bankingapp.exceptions;

public class InvalidJwtAuthenticationException extends RuntimeException{
    public InvalidJwtAuthenticationException(String message) {
        super(message);
    }

}
