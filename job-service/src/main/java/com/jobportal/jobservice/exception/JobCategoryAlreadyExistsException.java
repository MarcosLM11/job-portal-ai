package com.jobportal.jobservice.exception;

public class JobCategoryAlreadyExistsException extends JobServiceException {
    public JobCategoryAlreadyExistsException(String message) {
        super(message);
    }

    public JobCategoryAlreadyExistsException(String categoryName, Long categoryId) {
        super("Job category '" + categoryName + "' already exists with id: " + categoryId);
    }
}
