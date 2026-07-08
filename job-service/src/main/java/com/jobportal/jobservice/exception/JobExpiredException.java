package com.jobportal.jobservice.exception;

public class JobExpiredException extends JobServiceException {
    public JobExpiredException() {
        super("Job is expired or closed");
    }
}
