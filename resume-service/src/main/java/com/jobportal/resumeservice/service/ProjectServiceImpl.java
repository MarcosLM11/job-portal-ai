package com.jobportal.resumeservice.service;

import com.jobportal.resumeservice.dto.ProjectRequest;
import com.jobportal.resumeservice.dto.ProjectResponse;
import com.jobportal.resumeservice.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ResumeService resumeService;

    @Override
    public ProjectResponse addProject(Long resumeId, Long candidateId, ProjectRequest projectRequest) {
        return null;
    }

    @Override
    public List<ProjectResponse> getAllProjects(Long resumeId) {
        return List.of();
    }

    @Override
    public ProjectResponse updateProject(Long projectId, Long resumeId, Long candidateId, ProjectRequest projectRequest) {
        return null;
    }

    @Override
    public void deleteProject(Long projectId, Long resumeId, Long candidateId) {

    }
}
