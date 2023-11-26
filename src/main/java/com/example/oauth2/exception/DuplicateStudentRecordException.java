package com.example.oauth2.exception;

public class DuplicateStudentRecordException extends RuntimeException {
    public DuplicateStudentRecordException(String message) {
        super(message);
    }
}