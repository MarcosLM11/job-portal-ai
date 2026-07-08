package com.jobportal.jobservice.exception;

public class JobCompanyMismatchException extends JobServiceException {
    public JobCompanyMismatchException() {
        super("A job's company cannot be changed after creation");
    }
}
