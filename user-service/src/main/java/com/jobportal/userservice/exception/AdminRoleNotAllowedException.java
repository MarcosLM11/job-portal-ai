package com.jobportal.userservice.exception;

public class AdminRoleNotAllowedException extends UserServiceException {
    public AdminRoleNotAllowedException() { super("Admin role is not allowed"); }
}
