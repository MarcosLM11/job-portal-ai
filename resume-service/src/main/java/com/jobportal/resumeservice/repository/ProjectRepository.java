package com.jobportal.resumeservice.repository;

import com.jobportal.resumeservice.domain.Project;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByResume_IdOrderByDisplayOrderAsc(Long resumeId);
}
