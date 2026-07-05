package com.jobportal.userservice.exception;

public class AdminRoleNotAllowedException extends RuntimeException {

    public AdminRoleNotAllowedException() {
        super("Admin role is not allowed");
    }
}
