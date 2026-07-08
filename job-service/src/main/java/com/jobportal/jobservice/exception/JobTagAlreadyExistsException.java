package com.jobportal.jobservice.exception;

public class JobTagAlreadyExistsException extends JobServiceException {
    public JobTagAlreadyExistsException() {
        super("Job tag with this name already exists");
    }

    public JobTagAlreadyExistsException(String message) {
        super(message);
    }
}
