package com.jobportal.resumeservice.service;

import com.jobportal.resumeservice.dto.ProjectRequest;
import com.jobportal.resumeservice.dto.ProjectResponse;
import java.util.List;

public interface ProjectService {
    ProjectResponse addProject(Long resumeId, Long candidateId, ProjectRequest projectRequest);
    List<ProjectResponse> getAllProjects(Long resumeId);
    ProjectResponse updateProject(Long projectId, Long resumeId, Long candidateId, ProjectRequest projectRequest);
    void deleteProject(Long projectId,  Long resumeId, Long candidateId);
}
