package com.github.hkereb.swiftcodeapi.exceptions;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String parameter) {
        super("No record found for parameter: " + parameter);
    }
}
