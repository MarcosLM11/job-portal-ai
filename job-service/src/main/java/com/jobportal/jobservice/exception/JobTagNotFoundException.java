package com.jobportal.jobservice.exception;

public class JobTagNotFoundException extends JobServiceException {
    public JobTagNotFoundException(String message) {
        super(message);
    }

    public JobTagNotFoundException(Long tagId) {
        super("Job tag not found with id: " + tagId);
    }
}
