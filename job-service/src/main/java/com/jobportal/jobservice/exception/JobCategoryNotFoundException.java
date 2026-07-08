package com.jobportal.jobservice.exception;

public class JobCategoryNotFoundException extends JobServiceException {
    public JobCategoryNotFoundException(String message) {
        super(message);
    }

    public JobCategoryNotFoundException(Long categoryId) {
        super("Job category not found with id: " + categoryId);
    }
}
