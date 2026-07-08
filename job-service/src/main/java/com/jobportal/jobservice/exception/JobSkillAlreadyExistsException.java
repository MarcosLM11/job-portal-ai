package com.jobportal.jobservice.exception;

public class JobSkillAlreadyExistsException extends JobServiceException {
    public JobSkillAlreadyExistsException() {
        super("Job skill with this name already exists");
    }

    public JobSkillAlreadyExistsException(String message) {
        super(message);
    }
}
