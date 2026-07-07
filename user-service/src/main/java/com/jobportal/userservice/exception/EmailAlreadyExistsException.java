package com.jobportal.userservice.exception;

public class EmailAlreadyExistsException extends UserServiceException {
    public EmailAlreadyExistsException(String email) { super("Email already exists: " + email); }
}
