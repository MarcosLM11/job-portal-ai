package com.jobportal.jobservice.service;

import com.jobportal.jobservice.domain.JobCategory;
import com.jobportal.jobservice.dto.JobCategoryRequest;
import com.jobportal.jobservice.dto.JobCategoryResponse;
import com.jobportal.jobservice.exception.JobCategoryAlreadyExistsException;
import com.jobportal.jobservice.exception.JobCategoryNotFoundException;
import com.jobportal.jobservice.exception.JobCategoryParentException;
import com.jobportal.jobservice.repository.JobCategoryRepository;
import com.jobportal.jobservice.util.SlugGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.jobportal.jobservice.util.JobCategoryMapper.toDto;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class JobCategoryServiceImpl implements JobCategoryService {
    private final JobCategoryRepository jobCategoryRepository;

    @Override
    public JobCategoryResponse createCategory(JobCategoryRequest request) {
        if (Boolean.TRUE.equals(jobCategoryRepository.existsByName(request.name()))) throw new JobCategoryAlreadyExistsException("Category name already exists, choose a different name");

        JobCategory parent = null;

        if (request.parentId() != null) {
            parent = getCategoryEntityById(request.parentId());
        }

        var slug = SlugGenerator.generateUniqueSlug(request.name(), jobCategoryRepository::existsBySlug);

        var category = JobCategory.builder()
                .name(request.name())
                .slug(slug)
                .description(request.description())
                .iconUrl(request.iconUrl())
                .parent(parent)
                .build();

        var savedCategory = jobCategoryRepository.save(category);
        log.info("Category created: id={}, name={}", savedCategory.getId(), savedCategory.getName());

        return toDto(savedCategory, true);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobCategoryResponse> getAllCategories(Pageable pageable) {
        return jobCategoryRepository.findByActiveTrue(pageable).map(category -> toDto(category, false));
    }

    @Override
    @Transactional(readOnly = true)
    public JobCategoryResponse getCategoryById(Long id) {
        var jobCategory = getCategoryEntityById(id);
        return toDto(jobCategory, false);
    }

    @Override
    public JobCategoryResponse updateCategory(Long id, JobCategoryRequest request) {
        var category = getCategoryEntityById(id);
        if (!category.getName().equals(request.name()) && Boolean.TRUE.equals(jobCategoryRepository.existsByName(request.name()))) {
            throw new JobCategoryAlreadyExistsException("Category name already exists, choose a different name");
        }

        JobCategory parent = null;
        if (request.parentId() != null) {
            if (request.parentId().equals(id)) {
                throw new JobCategoryParentException("A category cannot be its own parent");
            }
            parent = getCategoryEntityById(request.parentId());
            assertNoCycle(id, parent);
        }

        category.setName(request.name());
        category.setDescription(request.description());
        category.setIconUrl(request.iconUrl());
        category.setParent(parent);
        var updatedCategory = jobCategoryRepository.save(category);
        log.info("Category updated: id={}", id);
        return toDto(updatedCategory, true);
    }

    @Override
    public void deleteCategory(Long id) {
        var category = getCategoryEntityById(id);
        deactivateRecursively(category);
        log.info("Category deactivated: id={}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public JobCategory getCategoryEntityById(Long id) {
        var category = jobCategoryRepository.findById(id).orElseThrow(() -> new JobCategoryNotFoundException("Category not found"));
        if (Boolean.FALSE.equals(category.getActive())) throw new JobCategoryNotFoundException("Category not found");
        return category;
    }

    private void assertNoCycle(Long id, JobCategory proposedParent) {
        var current = proposedParent;
        while (current != null) {
            if (current.getId().equals(id)) {
                throw new JobCategoryParentException("A category cannot be an ancestor of itself");
            }
            current = current.getParent();
        }
    }

    private void deactivateRecursively(JobCategory category) {
        category.setActive(false);
        jobCategoryRepository.save(category);
        category.getSubCategories().forEach(this::deactivateRecursively);
    }
}
