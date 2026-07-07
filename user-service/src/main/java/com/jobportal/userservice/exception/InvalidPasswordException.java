package com.jobportal.userservice.exception;

public class InvalidPasswordException extends UserServiceException {
    public InvalidPasswordException() { super("Invalid credentials"); }
}
