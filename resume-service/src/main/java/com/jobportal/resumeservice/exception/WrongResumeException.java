package com.jobportal.resumeservice.exception;

public class WrongResumeException extends RuntimeException {
    public WrongResumeException(String message) {
        super(message);
    }
}
