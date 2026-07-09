package com.jobportal.resumeservice.service;

import com.jobportal.resumeservice.domain.Education;
import com.jobportal.resumeservice.dto.EducationRequest;
import com.jobportal.resumeservice.dto.EducationResponse;
import com.jobportal.resumeservice.exception.EducationNotFoundException;
import com.jobportal.resumeservice.repository.EducationRepository;
import com.jobportal.resumeservice.util.EducationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.jobportal.resumeservice.util.EducationMapper.toDto;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {
    private final EducationRepository educationRepository;
    private final ResumeService resumeService;

    @Override
    public EducationResponse addEducation(Long resumeId, Long candidateId, EducationRequest request) {
        var resume = resumeService.getResumeEntityById(resumeId, candidateId);
        var education = Education.builder()
                .resume(resume)
                .institutionName(request.institutionName())
                .degree(request.degree())
                .fieldOfStudy(request.fieldOfStudy())
                .grade(request.grade())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .isCurrentlyStudiying(request.isCurrentlyStudiying())
                .description(request.description())
                .displayOrder(request.displayOrder())
                .build();
        return toDto(educationRepository.save(education));
    }

    @Override
    public List<EducationResponse> getEducations(Long resumeId) {
        return educationRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId)
                .stream()
                .map(EducationMapper::toDto)
                .toList();
    }

    @Override
    public EducationResponse updateEducation(Long educationId, Long resumeId, Long candidateId, EducationRequest request) {
        var education = educationRepository.findById(educationId)
                .filter(e -> e.getResume().getId().equals(resumeId))
                .filter(e -> e.getResume().getCandidateId().equals(candidateId))
                .orElseThrow( () -> new EducationNotFoundException("Education with id " + educationId + " not found for resume " + resumeId + " and candidate " + candidateId));

        education.setInstitutionName(request.institutionName());
        education.setDegree(request.degree());
        education.setFieldOfStudy(request.fieldOfStudy());
        education.setGrade(request.grade());
        education.setStartDate(request.startDate());
        education.setEndDate(request.endDate());
        education.setIsCurrentlyStudiying(request.isCurrentlyStudiying());
        education.setDescription(request.description());
        if (request.displayOrder() != null) education.setDisplayOrder(request.displayOrder());

        return toDto(educationRepository.save(education));
    }

    @Override
    public void deleteEducation(Long educationId, Long resumeId, Long candidateId) {
        var education = educationRepository.findById(educationId)
                .filter(e -> e.getResume().getId().equals(resumeId))
                .filter(e -> e.getResume().getCandidateId().equals(candidateId))
                .orElseThrow(() -> new EducationNotFoundException("Education with id " + educationId + " not found for resume " + resumeId + " and candidate " + candidateId));
        educationRepository.delete(education);
    }
}
