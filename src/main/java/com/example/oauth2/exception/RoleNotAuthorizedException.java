package com.example.oauth2.exception;

public class RoleNotAuthorizedException extends RuntimeException {
    public RoleNotAuthorizedException(String message) {
        super(message);
    }
}
