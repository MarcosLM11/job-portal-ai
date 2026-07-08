package com.jobportal.jobservice.util;

import com.jobportal.jobservice.domain.JobTag;
import com.jobportal.jobservice.dto.JobTagResponse;

public class JobTagMapper {

    public static JobTagResponse toDto(JobTag jobTag) {
        return JobTagResponse.builder()
                .id(jobTag.getId())
                .name(jobTag.getName())
                .slug(jobTag.getSlug())
                .active(jobTag.getActive())
                .build();
    }
}
