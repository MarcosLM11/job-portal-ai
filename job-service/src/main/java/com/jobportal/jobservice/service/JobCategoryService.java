package com.jobportal.jobservice.service;

import com.jobportal.jobservice.domain.JobCategory;
import com.jobportal.jobservice.dto.JobCategoryRequest;
import com.jobportal.jobservice.dto.JobCategoryResponse;
import java.util.List;

public interface JobCategoryService {
    JobCategoryResponse createCategory(JobCategoryRequest request);
    List<JobCategoryResponse> getAllCategories();
    JobCategoryResponse getCategoryById(Long id);
    JobCategoryResponse updateCategory(Long id, JobCategoryRequest request);
    void deleteCategory(Long id);
    JobCategory getCategoryEntityById(Long id);
}
