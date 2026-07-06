package com.jobportal.userservice.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String password) {
        super("Invalid password: " + password);
    }
}
