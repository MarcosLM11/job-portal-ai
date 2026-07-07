package com.jobportal.companyservice.exception;

public class CompanyNameAlreadyExistsException extends RuntimeException {
    public CompanyNameAlreadyExistsException(String message) {
        super(message);
    }
}
