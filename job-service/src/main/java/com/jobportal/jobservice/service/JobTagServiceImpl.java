package com.jobportal.jobservice.service;

import com.jobportal.jobservice.domain.JobTag;
import com.jobportal.jobservice.dto.JobTagRequest;
import com.jobportal.jobservice.dto.JobTagResponse;
import com.jobportal.jobservice.exception.JobTagAlreadyExistsException;
import com.jobportal.jobservice.exception.JobTagNotFoundException;
import com.jobportal.jobservice.repository.JobTagRepository;
import com.jobportal.jobservice.util.JobTagMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import static com.jobportal.jobservice.util.JobTagMapper.toDto;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class JobTagServiceImpl implements JobTagService {
    private final JobTagRepository jobTagRepository;

    @Override
    public JobTagResponse createTag(JobTagRequest request) {
        if (Boolean.TRUE.equals(jobTagRepository.existsByName(request.name()))) throw new JobTagAlreadyExistsException();

        var slug = generateUniqueSlug(request.name());
        var jobTag = JobTag.builder()
                .name(request.name())
                .slug(slug)
                .build();
        var savedTag = jobTagRepository.save(jobTag);
        log.info("Tag created: id={}, name={}", savedTag.getId(), savedTag.getName());
        return toDto(savedTag);
    }

    @Override
    public List<JobTagResponse> getAllTags() {
        return jobTagRepository.findAll().stream()
                .map(JobTagMapper::toDto)
                .toList();
    }

    @Override
    public JobTagResponse getById(Long id) {
        return toDto(getTagEntityById(id));
    }

    @Override
    public JobTagResponse updateTag(Long id, JobTagRequest request) {
        var jobTag = getTagEntityById(id);

        if (!jobTag.getName().equals(request.name()) && Boolean.TRUE.equals(jobTagRepository.existsByName(request.name()))) throw new JobTagAlreadyExistsException();

        jobTag.setName(request.name());
        var updatedTag = jobTagRepository.save(jobTag);
        log.info("Tag updated: id={}", id);
        return toDto(updatedTag);
    }

    @Override
    public void deleteTag(Long id) {
        var tagEntity = getTagEntityById(id);
        jobTagRepository.delete(tagEntity);
        log.info("Tag deleted: id={}", id);
    }

    @Override
    public JobTag getTagEntityById(Long id) {
        return jobTagRepository.findById(id).orElseThrow(() -> new JobTagNotFoundException("Job Tag not found with id: " + id));
    }

    @Override
    public Set<JobTag> getTagsByIds(Set<Long> tagIds) {
        return Set.copyOf(jobTagRepository.findAllById(tagIds));
    }

    private String generateUniqueSlug(String name) {
        var base = name.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .trim()
                .replaceAll("[\\s-]+", "-");

        if(Boolean.FALSE.equals(jobTagRepository.existsBySlug(base))) return base;

        var counter = 1;
        while (Boolean.TRUE.equals(jobTagRepository.existsBySlug(base+"-"+counter))) counter++;
        return base+"-"+counter;
    }
}
