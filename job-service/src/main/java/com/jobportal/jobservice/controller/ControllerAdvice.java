package com.jobportal.jobservice.controller;

import com.jobportal.jobservice.exception.JobNotFoundException;
import com.jobportal.jobservice.exception.JobExpiredException;
import com.jobportal.jobservice.exception.WrongEmployerException;
import com.jobportal.jobservice.exception.JobCategoryAlreadyExistsException;
import com.jobportal.jobservice.exception.JobCategoryNotFoundException;
import com.jobportal.jobservice.exception.JobCategoryParentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(JobNotFoundException.class)
    ProblemDetail handleJobNotFound(JobNotFoundException ex) {
        log.warn("Job Not Found", ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(JobExpiredException.class)
    ProblemDetail handleJobExpired(JobExpiredException ex) {
        log.warn("Job Expired", ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(WrongEmployerException.class)
    ProblemDetail handleWrongEmployer(WrongEmployerException ex) {
        log.warn("Wrong Employer", ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(JobCategoryAlreadyExistsException.class)
    ProblemDetail handleJobCategoryAlreadyExists(JobCategoryAlreadyExistsException ex) {
        log.warn("Job Category Already Exists", ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(JobCategoryNotFoundException.class)
    ProblemDetail handleJobCategoryNotFound(JobCategoryNotFoundException ex) {
        log.warn("Job Category Not Found", ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(JobCategoryParentException.class)
    ProblemDetail handleJobCategoryParent(JobCategoryParentException ex) {
        log.warn("Job Category Parent Error", ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
