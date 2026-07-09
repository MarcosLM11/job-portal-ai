package com.jobportal.resumeservice.service;

import com.jobportal.resumeservice.domain.PersonalInfo;
import com.jobportal.resumeservice.domain.Resume;
import com.jobportal.resumeservice.dto.PersonalInfoRequest;
import com.jobportal.resumeservice.dto.ResumeRequest;
import com.jobportal.resumeservice.dto.ResumeResponse;
import com.jobportal.resumeservice.exception.ResumeNotFoundException;
import com.jobportal.resumeservice.repository.ResumeRepository;
import com.jobportal.resumeservice.util.ResumeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.jobportal.resumeservice.util.ResumeMapper.toDto;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;

    @Override
    public ResumeResponse createResume(Long candidateId, ResumeRequest request) {

        if (Boolean.TRUE.equals(request.isDefault())) {
            resumeRepository.findByCandidateIdAndIsDefaultTrue(candidateId)
                    .ifPresent(resume -> {
                        resume.setIsDefault(false);
                        resumeRepository.save(resume);
                    });
        }

        var resume = Resume.builder()
                .candidateId(candidateId)
                .title(request.title())
                .template(request.template())
                .visibility(request.visibility())
                .isDefault(Boolean.TRUE.equals(request.isDefault()))
                .isActive(true)
                .build();

        return toDto(resumeRepository.save(resume));
    }

    @Override
    public ResumeResponse getResumeById(Long resumeId, Long candidateId) {
        return toDto(resumeRepository.findById(resumeId)
                .filter(resume -> resume.getCandidateId().equals(candidateId))
                .filter(Resume::getIsActive)
                .orElseThrow(() -> new ResumeNotFoundException("Resume not found")));
    }

    @Override
    public List<ResumeResponse> getMyResumes(Long candidateId) {
        return resumeRepository.findByCandidateId(candidateId)
                .stream()
                .filter(Resume::getIsActive)
                .map(ResumeMapper::toDto)
                .toList();
    }

    @Override
    public ResumeResponse updatePersonalInfo(Long resumeId, Long candidateId, PersonalInfoRequest personalInfoRequest) {
        var resume = getResumeEntityById(resumeId, candidateId);
        var personalInfo = resume.getPersonalInfo();
        if (personalInfo == null) personalInfo = new PersonalInfo();

        if (personalInfoRequest.firstName() != null) personalInfo.setFirstName(personalInfoRequest.firstName());
        if (personalInfoRequest.lastName() != null) personalInfo.setLastName(personalInfoRequest.lastName());
        if (personalInfoRequest.email() != null) personalInfo.setEmail(personalInfoRequest.email());
        if (personalInfoRequest.phone() != null) personalInfo.setPhone(personalInfoRequest.phone());
        if (personalInfoRequest.city() != null) personalInfo.setCity(personalInfoRequest.city());
        if (personalInfoRequest.country() != null) personalInfo.setCountry(personalInfoRequest.country());
        if (personalInfoRequest.linkedinUrl() != null) personalInfo.setLinkedinUrl(personalInfoRequest.linkedinUrl());
        if (personalInfoRequest.githubUrl() != null) personalInfo.setGithubUrl(personalInfoRequest.githubUrl());
        if (personalInfoRequest.portfolioUrl() != null) personalInfo.setPortfolioUrl(personalInfoRequest.portfolioUrl());
        if (personalInfoRequest.websiteUrl() != null) personalInfo.setWebsiteUrl(personalInfoRequest.websiteUrl());

        resume.setPersonalInfo(personalInfo);
        return toDto(resumeRepository.save(resume));
    }

    @Override
    public ResumeResponse updateSummary(Long resumeId, Long candidateId, String summary) {
        var resume = getResumeEntityById(resumeId, candidateId);
        resume.setSummary(summary);
        return toDto(resumeRepository.save(resume));
    }

    @Override
    public ResumeResponse setDefaultResume(Long resumeId, Long candidateId) {
        var resume = getResumeEntityById(resumeId, candidateId);
        if (Boolean.TRUE.equals(resume.getIsDefault())) {
            return toDto(resume);
        }

        resumeRepository.findByCandidateIdAndIsDefaultTrue(candidateId)
                .ifPresent(r -> {
                    r.setIsDefault(false);
                    resumeRepository.save(r);
                });

        resume.setIsDefault(true);
        return toDto(resumeRepository.save(resume));
    }

    @Override
    public void deleteResume(Long resumeId, Long candidateId) {
        var resume = getResumeEntityById(resumeId, candidateId);
        resume.setIsActive(false);
        resume.setIsDefault(false);
        resumeRepository.save(resume);
    }

    @Override
    public Resume getResumeEntityById(Long resumeId, Long candidateId) {
        return resumeRepository.findById(resumeId)
                .filter(resume -> resume.getCandidateId().equals(candidateId))
                .filter(Resume::getIsActive)
                .orElseThrow(() -> new ResumeNotFoundException("Resume not found"));
    }
}
