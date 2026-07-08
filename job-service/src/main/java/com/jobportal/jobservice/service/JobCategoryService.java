package com.jobportal.jobservice.service;

import com.jobportal.jobservice.domain.JobCategory;
import com.jobportal.jobservice.dto.JobCategoryRequest;
import com.jobportal.jobservice.dto.JobCategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobCategoryService {
    JobCategoryResponse createCategory(JobCategoryRequest request);
    Page<JobCategoryResponse> getAllCategories(Pageable pageable);
    JobCategoryResponse getCategoryById(Long id);
    JobCategoryResponse updateCategory(Long id, JobCategoryRequest request);
    void deleteCategory(Long id);
    JobCategory getCategoryEntityById(Long id);
}
