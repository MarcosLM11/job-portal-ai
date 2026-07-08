package com.jobportal.jobservice.service;

import com.jobportal.jobservice.domain.JobSkill;
import com.jobportal.jobservice.dto.JobSkillRequest;
import com.jobportal.jobservice.dto.JobSkillResponse;
import com.jobportal.jobservice.exception.JobSkillAlreadyExistsException;
import com.jobportal.jobservice.exception.JobSkillNotFoundException;
import com.jobportal.jobservice.repository.JobSkillRepository;
import com.jobportal.jobservice.util.JobSkillMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static com.jobportal.jobservice.util.JobSkillMapper.toDto;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class JobSkillServiceImpl implements JobSkillService {
    private final JobSkillRepository jobSkillRepository;

    @Override
    public JobSkillResponse createSkill(JobSkillRequest request) {
        if (Boolean.TRUE.equals(jobSkillRepository.existsByName(request.name()))) throw new JobSkillAlreadyExistsException();

        var slug = generateUniqueSlug(request.name());
        var jobSkill = JobSkill.builder()
                .name(request.name())
                .slug(slug)
                .category(request.category())
                .build();
        var savedJobSkill = jobSkillRepository.save(jobSkill);
        log.info("Skill created: id={}, name={}", savedJobSkill.getId(), savedJobSkill.getName());
        return toDto(savedJobSkill);
    }

    @Override
    public List<JobSkillResponse> getAllSkills() {
        return jobSkillRepository.findByActiveTrue().stream()
                .map(JobSkillMapper::toDto)
                .toList();
    }

    @Override
    public JobSkillResponse getSkillById(Long id) {
        return jobSkillRepository.findById(id)
                .filter(JobSkill::getActive)
                .map(JobSkillMapper::toDto)
                .orElseThrow(() -> new JobSkillNotFoundException(id));
    }

    @Override
    public JobSkillResponse updateSkill(Long id, JobSkillRequest request) {
        var jobSkill = jobSkillRepository.findById(id).orElseThrow(() -> new JobSkillNotFoundException(id));

        if (!jobSkill.getName().equals(request.name()) && Boolean.TRUE.equals(jobSkillRepository.existsByName(request.name()))) throw new JobSkillAlreadyExistsException();

        jobSkill.setName(request.name());
        jobSkill.setCategory(request.category());
        var updatedSkill = jobSkillRepository.save(jobSkill);
        log.info("Skill updated: id={}", id);
        return toDto(updatedSkill);
    }

    @Override
    public void deleteSkillById(Long id) {
        var jobSkill = jobSkillRepository.findById(id).orElseThrow(() -> new JobSkillNotFoundException(id));
        jobSkill.setActive(false);
        jobSkillRepository.save(jobSkill);
        log.info("Skill deactivated: id={}", id);
    }

    @Override
    public Set<JobSkill> getSkillsByIds(Set<Long> ids) {
        return new HashSet<>(jobSkillRepository.findAllById(ids));
    }

    private String generateUniqueSlug(String name) {
        var base = name.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .trim()
                .replaceAll("[\\s-]+", "-");

        if(Boolean.FALSE.equals(jobSkillRepository.existsBySlug(base))) return base;

        var counter = 1;
        while (Boolean.TRUE.equals(jobSkillRepository.existsBySlug(base+"-"+counter))) counter++;
        return base+"-"+counter;
    }
}
