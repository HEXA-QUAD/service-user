package com.example.oauth2.controller;

import com.example.oauth2.exception.DuplicateStudentRecordException;
import com.example.oauth2.exception.NoSuchAccountException;
import com.example.oauth2.exception.RoleNotAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(RoleNotAuthorizedException.class)
    public final ResponseEntity<String> handleResponderNotAvailableExceptions(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NoSuchAccountException.class)
    public final ResponseEntity<String> handleNoSuchAccountException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateStudentRecordException.class)
    public final ResponseEntity<String> handleDuplicateStudentRecordException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
