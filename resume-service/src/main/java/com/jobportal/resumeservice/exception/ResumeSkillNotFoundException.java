package com.jobportal.resumeservice.exception;

public class ResumeSkillNotFoundException extends RuntimeException {
    public ResumeSkillNotFoundException() {
        super("Resume skill not found");
    }
}
