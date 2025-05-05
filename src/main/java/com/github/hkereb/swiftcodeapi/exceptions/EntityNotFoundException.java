package com.github.hkereb.swiftcodeapi.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String parameter, String message) {
        super(message + ": " + parameter);
    }
}
