package com.jobportal.jobservice.service;

import com.jobportal.jobservice.domain.JobSkill;
import com.jobportal.jobservice.dto.JobSkillRequest;
import com.jobportal.jobservice.dto.JobSkillResponse;
import com.jobportal.jobservice.exception.JobSkillAlreadyExistsException;
import com.jobportal.jobservice.exception.JobSkillNotFoundException;
import com.jobportal.jobservice.repository.JobSkillRepository;
import com.jobportal.jobservice.util.JobSkillMapper;
import com.jobportal.jobservice.util.SlugGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
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

        var slug = SlugGenerator.generateUniqueSlug(request.name(), jobSkillRepository::existsBySlug);
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
    @Transactional(readOnly = true)
    public Page<JobSkillResponse> getAllSkills(Pageable pageable) {
        return jobSkillRepository.findByActiveTrue(pageable).map(JobSkillMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
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
    public void deleteSkill(Long id) {
        var jobSkill = jobSkillRepository.findById(id).orElseThrow(() -> new JobSkillNotFoundException(id));
        jobSkill.setActive(false);
        jobSkillRepository.save(jobSkill);
        log.info("Skill deactivated: id={}", id);
    }

    @Override
    public Set<JobSkill> getSkillsByIds(Set<Long> ids) {
        return new HashSet<>(jobSkillRepository.findAllById(ids));
    }
}
