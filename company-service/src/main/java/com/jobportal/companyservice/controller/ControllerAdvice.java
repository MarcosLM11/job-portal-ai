package com.jobportal.companyservice.controller;

import com.jobportal.companyservice.exception.AlreadyHaveCompanyException;
import com.jobportal.companyservice.exception.CompanyAlreadyExistsException;
import com.jobportal.companyservice.exception.CompanyNameAlreadyExistsException;
import com.jobportal.companyservice.exception.CompanyNotFoundException;
import com.jobportal.companyservice.exception.NotOwnerException;
import com.jobportal.companyservice.exception.RegistrationNumberAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(CompanyNotFoundException.class)
    ProblemDetail handleCompanyNotFound(CompanyNotFoundException ex) {
        log.error("Company Not Found", ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(CompanyAlreadyExistsException.class)
    ProblemDetail handleCompanyAlreadyExists(CompanyAlreadyExistsException ex) {
        log.error("Company Already Exists", ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(CompanyNameAlreadyExistsException.class)
    ProblemDetail handleCompanyNameAlreadyExists(CompanyNameAlreadyExistsException ex) {
        log.error("Company Name Already Exists", ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(RegistrationNumberAlreadyExistsException.class)
    ProblemDetail handleRegistrationNumberAlreadyExists(RegistrationNumberAlreadyExistsException ex) {
        log.error("Registration Number Already Exists", ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(AlreadyHaveCompanyException.class)
    ProblemDetail handleAlreadyHaveCompany(AlreadyHaveCompanyException ex) {
        log.error("User Already Has A Company", ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(NotOwnerException.class)
    ProblemDetail handleNotOwner(NotOwnerException ex) {
        log.error("Not Owner", ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
    }
}
