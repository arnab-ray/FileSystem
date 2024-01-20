package org.example.exceptions;

public class UnavailableSpaceException extends RuntimeException {
    public UnavailableSpaceException(String message) {
        super(message);
    }
}
