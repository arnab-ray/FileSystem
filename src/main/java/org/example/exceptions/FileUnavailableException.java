package org.example.exceptions;

public class FileUnavailableException extends RuntimeException {
    public FileUnavailableException(String message) {
        super(message);
    }
}
