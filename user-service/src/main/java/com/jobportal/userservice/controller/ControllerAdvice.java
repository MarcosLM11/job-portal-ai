package com.jobportal.userservice.controller;

import com.jobportal.userservice.exception.AdminRoleNotAllowedException;
import com.jobportal.userservice.exception.EmailAlreadyExistsException;
import com.jobportal.userservice.exception.InvalidPasswordException;
import com.jobportal.userservice.exception.UserNotExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    ProblemDetail handleEmailAlreadyExists(EmailAlreadyExistsException ex) {
        log.error("Email Already Exists", ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(AdminRoleNotAllowedException.class)
    ProblemDetail handleAdminRoleNotAllowed(AdminRoleNotAllowedException ex) {
        log.error("Admin Role Not Allowed", ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    ProblemDetail handleInvalidPassword(InvalidPasswordException ex) {
        log.error("Invalid Password", ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(UserNotExistsException.class)
    ProblemDetail handleUserNotExists(UserNotExistsException ex) {
        log.error("User Not Exists", ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }
}
