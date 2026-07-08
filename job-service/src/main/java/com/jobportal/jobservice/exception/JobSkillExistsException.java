package com.jobportal.jobservice.exception;

public class JobSkillExistsException extends JobServiceException {
    public JobSkillExistsException() {
        super("Job skill with this name already exists");
    }

    public JobSkillExistsException(String message) {
        super(message);
    }
}
