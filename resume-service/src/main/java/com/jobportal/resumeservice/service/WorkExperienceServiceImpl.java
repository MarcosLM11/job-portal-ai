package com.jobportal.resumeservice.service;

import com.jobportal.resumeservice.domain.WorkExperience;
import com.jobportal.resumeservice.dto.AddWorkExperience;
import com.jobportal.resumeservice.dto.WorkExperienceResponse;
import com.jobportal.resumeservice.repository.WorkExperienceRepository;
import com.jobportal.resumeservice.util.WorkExperienceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.jobportal.resumeservice.util.WorkExperienceMapper.toDto;

@Service
@RequiredArgsConstructor
public class WorkExperienceServiceImpl implements WorkExperienceService {
    private final WorkExperienceRepository workExperienceRepository;
    private final ResumeService resumeService;

    @Override
    public WorkExperienceResponse addWorkExperience(Long resumeId, Long candidateId, AddWorkExperience request) {
        var resume = resumeService.getResumeEntityById(resumeId,candidateId);
        var workExperience = WorkExperience.builder()
                .resume(resume)
                .companyName(request.companyName())
                .companyLogoUrl(request.companyLogoUrl())
                .jobTitle(request.jobTitle())
                .employmentType(request.employmentType())
                .location(request.location())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .isCurrentJob(Boolean.TRUE.equals(request.isCurrentJob()))
                .description(request.description())
                .technologies(request.technologies() != null ? request.technologies() : List.of())
                .displayOrder(request.displayOrder() != null ? request.displayOrder() : 0)
                .build();
        return toDto(workExperienceRepository.save(workExperience));
    }

    @Override
    public List<WorkExperienceResponse> getWorkExperiences(Long resumeId) {
        return workExperienceRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId)
                .stream()
                .map(WorkExperienceMapper::toDto)
                .toList();
    }

    @Override
    public WorkExperienceResponse updateWorkExperience(Long resumeId, Long candidateId, Long workExperienceId, AddWorkExperience request) {
        var workExperience = getWorkExperienceEntityById(workExperienceId);

        if (!workExperience.getResume().getId().equals(resumeId)) throw new WrongResumeException();
        if (!workExperience.getResume().getCandidateId().equals(candidateId)) throw new WrongCandidateException();

        workExperience.setCompanyName(request.companyName());
        workExperience.setCompanyLogoUrl(request.companyLogoUrl());
        workExperience.setJobTitle(request.jobTitle());
        workExperience.setEmploymentType(request.employmentType());
        workExperience.setLocation(request.location());
        workExperience.setStartDate(request.startDate());
        workExperience.setEndDate(request.endDate());
        workExperience.setIsCurrentJob(Boolean.TRUE.equals(request.isCurrentJob()));
        workExperience.setDescription(request.description());

        if (request.technologies() != null) workExperience.setTechnologies(request.technologies());
        if (request.displayOrder() != null) workExperience.setDisplayOrder(request.displayOrder());

        return toDto(workExperienceRepository.save(workExperience));
    }

    @Override
    public void deleteWorkExperience(Long resumeId, Long workExperienceId, Long candidateId) {
        var workExperience = getWorkExperienceEntityById(workExperienceId);
        if (!workExperience.getResume().getId().equals(resumeId)) throw new WrongResumeException();
        if (!workExperience.getResume().getCandidateId().equals(candidateId)) throw new WrongCandidateException();
        workExperienceRepository.delete(workExperience);
    }

    @Override
    public WorkExperience getWorkExperienceEntityById(Long workExperienceId) {
        return workExperienceRepository.findById(workExperienceId)
                .orElseThrow(() -> new WorkExperienceNotFoundException("Work experience not found"));
    }
}
