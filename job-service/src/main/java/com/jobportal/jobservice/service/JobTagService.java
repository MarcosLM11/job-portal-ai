package com.jobportal.jobservice.service;

import com.jobportal.jobservice.domain.JobTag;
import com.jobportal.jobservice.dto.JobTagRequest;
import com.jobportal.jobservice.dto.JobTagResponse;
import java.util.List;
import java.util.Set;

public interface JobTagService {
    JobTagResponse createTag(JobTagRequest request);
    List<JobTagResponse> getAllTags();
    JobTagResponse getById(Long id);
    JobTagResponse updateTag(Long id, JobTagRequest request);
    void deleteTag(Long id);
    JobTag getTagEntityById(Long id);
    Set<JobTag> getTagsByIds(Set<Long> tagIds);
}
