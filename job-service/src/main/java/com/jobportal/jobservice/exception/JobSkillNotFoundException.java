package com.jobportal.jobservice.exception;

public class JobSkillNotFoundException extends JobServiceException {
    public JobSkillNotFoundException(String message) {
        super(message);
    }

    public JobSkillNotFoundException(Long skillId) {
        super("Job skill not found with id: " + skillId);
    }
}
