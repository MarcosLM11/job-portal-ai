package com.jobportal.resumeservice.service;

import com.jobportal.resumeservice.domain.ResumeSkill;
import com.jobportal.resumeservice.dto.ResumeSkillRequest;
import com.jobportal.resumeservice.dto.ResumeSkillResponse;
import com.jobportal.resumeservice.exception.ResumeSkillNotFoundException;
import com.jobportal.resumeservice.repository.ResumeSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.jobportal.resumeservice.util.ResumeSkillMapper.toDto;

@Service
@RequiredArgsConstructor
public class ResumeSkillServiceImpl implements ResumeSkillService {
    private final ResumeSkillRepository resumeSkillRepository;
    private final ResumeService resumeService;

    @Override
    public ResumeSkillResponse addSkill(Long resumeId, Long candidateId, ResumeSkillRequest request) {
        var resume = resumeService.getResumeEntityById(resumeId, candidateId);
        var resumeSkill = ResumeSkill.builder()
                .yearsOfExperience(request.yearsOfExperience())
                .skillName(request.skillName())
                .resume(resume)
                .proficiencyLevel(request.proficiencyLevel())
                .displayOrder(request.displayOrder())
                .build();
        return toDto(resumeSkillRepository.save(resumeSkill));
    }

    @Override
    public List<ResumeSkillResponse> getSkills(Long resumeId) {
        return List.of();
    }

    @Override
    public ResumeSkillResponse updateSkill(Long skillId, Long resumeId, Long candidateId, ResumeSkillRequest request) {
        var resumeSkill = resumeSkillRepository.findById(skillId)
                .filter(r -> r.getResume().getId().equals(resumeId))
                .filter(r -> r.getResume().getCandidateId().equals(candidateId))
                .orElseThrow(ResumeSkillNotFoundException::new);

        resumeSkill.setSkillName(request.skillName());
        resumeSkill.setProficiencyLevel(request.proficiencyLevel());
        resumeSkill.setYearsOfExperience(request.yearsOfExperience());

        if (request.displayOrder() != null) resumeSkill.setDisplayOrder(request.displayOrder());

        return toDto(resumeSkillRepository.save(resumeSkill));
    }

    @Override
    public void deleteSkill(Long resumeId, Long skillId, Long candidateId) {
        var resumeSkill = resumeSkillRepository.findById(skillId)
                .filter(r -> r.getResume().getId().equals(resumeId))
                .filter(r -> r.getResume().getCandidateId().equals(candidateId))
                .orElseThrow(ResumeSkillNotFoundException::new);
        resumeSkillRepository.delete(resumeSkill);
    }
}
