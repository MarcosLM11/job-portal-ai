package com.jobportal.jobservice.repository;

import com.jobportal.jobservice.domain.JobCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobCategoryRepository extends JpaRepository<JobCategory, Long> {

    Boolean existsByName(String name);
    Boolean existsBySlug(String slug);
    Page<JobCategory> findByActiveTrue(Pageable pageable);

}
