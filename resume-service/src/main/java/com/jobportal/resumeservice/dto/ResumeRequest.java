package com.jobportal.resumeservice.dto;

import com.jobportal.resumeservice.domain.ResumeTemplate;
import com.jobportal.resumeservice.domain.ResumeVisibility;

public record ResumeRequest(
        String title,
        ResumeTemplate template,
        ResumeVisibility visibility,
        Boolean isDefault
) {
}
