package com.jobportal.jobservice.controller;

import com.jobportal.jobservice.dto.JobCategoryRequest;
import com.jobportal.jobservice.dto.JobCategoryResponse;
import com.jobportal.jobservice.service.JobCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/job-categories")
@RequiredArgsConstructor
public class JobCategoryController {
    private final JobCategoryService jobCategoryService;

    @PostMapping
    public ResponseEntity<JobCategoryResponse> createCategory(@RequestBody @Valid JobCategoryRequest jobCategoryRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobCategoryService.createCategory(jobCategoryRequest));
    }

    @GetMapping
    public ResponseEntity<List<JobCategoryResponse>> getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(jobCategoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobCategoryResponse> getCategoryById(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(jobCategoryService.getCategoryById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobCategoryResponse> updateCategory(@PathVariable long id, @RequestBody @Valid JobCategoryRequest jobCategoryRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(jobCategoryService.updateCategory(id, jobCategoryRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable long id) {
        jobCategoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
