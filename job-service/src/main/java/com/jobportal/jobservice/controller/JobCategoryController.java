package com.jobportal.jobservice.controller;

import com.jobportal.jobservice.dto.JobCategoryRequest;
import com.jobportal.jobservice.dto.JobCategoryResponse;
import com.jobportal.jobservice.service.JobCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/job-categories")
@RequiredArgsConstructor
public class JobCategoryController {
    private final JobCategoryService jobCategoryService;

    @PostMapping
    public ResponseEntity<JobCategoryResponse> createCategory(@RequestBody @Valid JobCategoryRequest jobCategoryRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobCategoryService.createCategory(jobCategoryRequest));
    }

    @GetMapping
    public ResponseEntity<Page<JobCategoryResponse>> getAllCategories(@PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(jobCategoryService.getAllCategories(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobCategoryResponse> getCategoryById(@PathVariable long id) {
        return ResponseEntity.ok(jobCategoryService.getCategoryById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobCategoryResponse> updateCategory(@PathVariable long id, @RequestBody @Valid JobCategoryRequest jobCategoryRequest) {
        return ResponseEntity.ok(jobCategoryService.updateCategory(id, jobCategoryRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable long id) {
        jobCategoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
