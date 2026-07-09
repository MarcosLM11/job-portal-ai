package com.jobportal.resumeservice.exception;

public class ResumeNotFoundException extends RuntimeException {
    public ResumeNotFoundException(String message) {
        super(message);
    }
}
