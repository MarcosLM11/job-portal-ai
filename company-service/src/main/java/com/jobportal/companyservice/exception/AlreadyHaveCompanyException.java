package com.jobportal.companyservice.exception;

public class AlreadyHaveCompanyException extends RuntimeException {
    public AlreadyHaveCompanyException(String message) {
        super(message);
    }
}
