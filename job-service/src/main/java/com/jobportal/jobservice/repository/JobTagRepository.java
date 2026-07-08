package com.jobportal.jobservice.repository;

import com.jobportal.jobservice.domain.JobTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobTagRepository extends JpaRepository<JobTag, Long> {
    Boolean existsByName(String name);
    Boolean existsBySlug(String slug);
    Page<JobTag> findByActiveTrue(Pageable pageable);
}
