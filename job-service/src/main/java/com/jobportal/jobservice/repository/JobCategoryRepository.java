package com.jobportal.jobservice.repository;

import com.jobportal.jobservice.domain.JobCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobCategoryRepository extends JpaRepository<JobCategory, Long> {

    Boolean existsByName(String name);
    Boolean existsBySlug(String slug);
    List<JobCategory> findByActiveTrue();

}
