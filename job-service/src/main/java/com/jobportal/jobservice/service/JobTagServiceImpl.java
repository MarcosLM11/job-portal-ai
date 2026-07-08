package com.jobportal.jobservice.service;

import com.jobportal.jobservice.domain.JobTag;
import com.jobportal.jobservice.dto.JobTagRequest;
import com.jobportal.jobservice.dto.JobTagResponse;
import com.jobportal.jobservice.exception.JobTagAlreadyExistsException;
import com.jobportal.jobservice.exception.JobTagNotFoundException;
import com.jobportal.jobservice.repository.JobTagRepository;
import com.jobportal.jobservice.util.JobTagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

import static com.jobportal.jobservice.util.JobTagMapper.toDto;

@Service
@RequiredArgsConstructor
public class JobTagServiceImpl implements JobTagService {
    private final JobTagRepository jobTagRepository;

    @Override
    public JobTagResponse createTag(JobTagRequest request) {
        if (jobTagRepository.existsByName(request.name())) throw new JobTagAlreadyExistsException();

        var slug = generateUniqueSlug(request.name());
        var jobTag = JobTag.builder()
                .name(request.name())
                .slug(slug)
                .build();
        return toDto(jobTagRepository.save(jobTag));
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

        if (!jobTag.getName().equals(request.name()) && jobTagRepository.existsByName(request.name())) throw new JobTagAlreadyExistsException();

        jobTag.setName(request.name());
        return toDto(jobTagRepository.save(jobTag));
    }

    @Override
    public void deleteTag(Long id) {
        var tagEntity = getTagEntityById(id);
        jobTagRepository.delete(tagEntity);
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

        if(!jobTagRepository.existsBySlug(base)) return base;

        var counter = 1;
        while (jobTagRepository.existsBySlug(base+"-"+counter)) counter++;
        return base+"-"+counter;
    }
}
