package com.jobportal.companyservice.exception;

public class RegistrationNumberAlreadyExistsException extends RuntimeException {
    public RegistrationNumberAlreadyExistsException(String message) {
        super(message);
    }
}
