package com.jobportal.jobservice.service;

import com.jobportal.jobservice.domain.JobSkill;
import com.jobportal.jobservice.dto.JobSkillRequest;
import com.jobportal.jobservice.dto.JobSkillResponse;
import com.jobportal.jobservice.exception.JobSkillAlreadyExistsException;
import com.jobportal.jobservice.exception.JobSkillNotFoundException;
import com.jobportal.jobservice.repository.JobSkillRepository;
import com.jobportal.jobservice.util.JobSkillMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static com.jobportal.jobservice.util.JobSkillMapper.toDto;

@Service
@RequiredArgsConstructor
public class JobSkillServiceImpl implements JobSkillService {
    private static final String ERROR = "Job Skill already exists ";
    private final JobSkillRepository jobSkillRepository;

    @Override
    public JobSkillResponse createSkill(JobSkillRequest request) {
        if (jobSkillRepository.existsByName(request.name())) throw new JobSkillAlreadyExistsException();

        var slug = generateUniqueSlug(request.name());
        var jobSkill = JobSkill.builder()
                .name(request.name())
                .slug(slug)
                .category(request.category())
                .build();
        var savedJobSkill = jobSkillRepository.save(jobSkill);
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
                .map(JobSkillMapper::toDto)
                .orElseThrow(() -> new JobSkillNotFoundException(ERROR + id));
    }

    @Override
    public JobSkillResponse updateSkill(Long id, JobSkillRequest request) {
        var jobSkill = jobSkillRepository.findById(id).orElseThrow(() -> new JobSkillNotFoundException(ERROR + id));

        if (!jobSkill.getName().equals(request.name()) && jobSkillRepository.existsByName(request.name())) throw new JobSkillAlreadyExistsException();

        jobSkill.setName(request.name());
        jobSkill.setCategory(request.category());
        return toDto(jobSkillRepository.save(jobSkill));
    }

    @Override
    public void deleteSkillById(Long id) {
        var jobSkill = jobSkillRepository.findById(id).orElseThrow(() -> new JobSkillNotFoundException(ERROR + id));
        jobSkill.setActive(false);
        jobSkillRepository.save(jobSkill);
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

        if(!jobSkillRepository.existsBySlug(base)) return base;

        var counter = 1;
        while (jobSkillRepository.existsBySlug(base+"-"+counter)) counter++;
        return base+"-"+counter;
    }
}
