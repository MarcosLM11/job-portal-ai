package com.jobportal.userservice.exception;

public class UserNotExistsException extends RuntimeException {
    public UserNotExistsException() {
        super("User with that email does not exist");
    }
}
