package com.jobportal.jobservice.service;

import com.jobportal.jobservice.domain.JobTag;
import com.jobportal.jobservice.dto.JobTagRequest;
import com.jobportal.jobservice.dto.JobTagResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Set;

public interface JobTagService {
    JobTagResponse createTag(JobTagRequest request);
    Page<JobTagResponse> getAllTags(Pageable pageable);
    JobTagResponse getTagById(Long id);
    JobTagResponse updateTag(Long id, JobTagRequest request);
    void deleteTag(Long id);
    JobTag getTagEntityById(Long id);
    Set<JobTag> getTagsByIds(Set<Long> tagIds);
}
