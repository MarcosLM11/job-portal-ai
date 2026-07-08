package com.jobportal.jobservice.exception;

public class JobNotFoundException extends JobServiceException {
    public JobNotFoundException() {
        super("Job not found");
    }

    public JobNotFoundException(Long jobId) {
        super("Job not found with id: " + jobId);
    }
}
