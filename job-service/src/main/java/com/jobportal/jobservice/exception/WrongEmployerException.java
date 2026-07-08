package com.jobportal.jobservice.exception;

public class WrongEmployerException extends JobServiceException {
    public WrongEmployerException() {
        super("Employer is not authorized to perform this action");
    }
}
