package com.jobportal.jobservice.exception;

public class JobCategoryParentException extends JobServiceException {
    public JobCategoryParentException(String message) {
        super(message);
    }

    public JobCategoryParentException(Long categoryId) {
        super("Category " + categoryId + " cannot be its own parent");
    }
}
