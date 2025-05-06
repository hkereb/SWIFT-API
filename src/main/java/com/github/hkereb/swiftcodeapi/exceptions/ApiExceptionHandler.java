package com.github.hkereb.swiftcodeapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<Object> handleRecordNotFound(EntityNotFoundException ex) {
        HttpStatus recordNotFoundStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        ApiException apiException = new ApiException(
                ex.getMessage(),
                recordNotFoundStatus
        );
        return new ResponseEntity<>(apiException, recordNotFoundStatus);
    }

    @ExceptionHandler(value = InvalidInputException.class)
    public ResponseEntity<Object> handleInvalidInputException(InvalidInputException ex) {
        HttpStatus invalidInputExceptionStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        ApiException apiException = new ApiException(
                ex.getMessage(),
                invalidInputExceptionStatus
        );
        return new ResponseEntity<>(apiException, invalidInputExceptionStatus);
    }

    @ExceptionHandler(value = MissingPathVariableException.class)
    public ResponseEntity<Object> handleMissingPathVariable(InvalidInputException ex) {
        HttpStatus missingPathVariableStatus = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(
                ex.getMessage(),
                missingPathVariableStatus
        );
        return new ResponseEntity<>(apiException, missingPathVariableStatus);
    }
}
