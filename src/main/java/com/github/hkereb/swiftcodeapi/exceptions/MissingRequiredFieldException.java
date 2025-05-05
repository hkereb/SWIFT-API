package com.github.hkereb.swiftcodeapi.exceptions;

public class MissingRequiredFieldException extends Exception {
    public MissingRequiredFieldException(String message) {
        super(message);
    }
}
