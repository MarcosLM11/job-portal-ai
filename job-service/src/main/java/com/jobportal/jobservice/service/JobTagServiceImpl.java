package com.jobportal.jobservice.service;

import com.jobportal.jobservice.domain.JobTag;
import com.jobportal.jobservice.dto.JobTagRequest;
import com.jobportal.jobservice.dto.JobTagResponse;
import com.jobportal.jobservice.exception.JobTagAlreadyExistsException;
import com.jobportal.jobservice.exception.JobTagNotFoundException;
import com.jobportal.jobservice.repository.JobTagRepository;
import com.jobportal.jobservice.util.JobTagMapper;
import com.jobportal.jobservice.util.SlugGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

        var slug = SlugGenerator.generateUniqueSlug(request.name(), jobTagRepository::existsBySlug);
        var jobTag = JobTag.builder()
                .name(request.name())
                .slug(slug)
                .build();
        var savedTag = jobTagRepository.save(jobTag);
        log.info("Tag created: id={}, name={}", savedTag.getId(), savedTag.getName());
        return toDto(savedTag);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobTagResponse> getAllTags(Pageable pageable) {
        return jobTagRepository.findByActiveTrue(pageable).map(JobTagMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public JobTagResponse getTagById(Long id) {
        var tag = getTagEntityById(id);
        if (Boolean.FALSE.equals(tag.getActive())) throw new JobTagNotFoundException(id);
        return toDto(tag);
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
        tagEntity.setActive(false);
        jobTagRepository.save(tagEntity);
        log.info("Tag deactivated: id={}", id);
    }

    @Override
    public JobTag getTagEntityById(Long id) {
        return jobTagRepository.findById(id).orElseThrow(() -> new JobTagNotFoundException("Job Tag not found with id: " + id));
    }

    @Override
    public Set<JobTag> getTagsByIds(Set<Long> tagIds) {
        return Set.copyOf(jobTagRepository.findAllById(tagIds));
    }
}
