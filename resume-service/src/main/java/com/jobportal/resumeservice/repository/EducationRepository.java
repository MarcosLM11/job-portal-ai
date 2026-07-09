package com.jobportal.resumeservice.repository;

import com.jobportal.resumeservice.domain.Education;
import com.jobportal.resumeservice.dto.EducationResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {
    List<Education> findByResume_IdOrderByDisplayOrderAsc(Long resumeId);

}
