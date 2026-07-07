package com.jobportal.userservice.exception;

public class UserNotExistsException extends UserServiceException {
    public UserNotExistsException() { super("User not found"); }
    public UserNotExistsException(String context) { super("User not found: " + context); }
}
