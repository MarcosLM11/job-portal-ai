package com.jobportal.jobservice.dto;

import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record JobCategoryResponse(
    Long id,
    String name,
    String slug,
    String description,
    String iconUrl,
    Boolean active,
    Long parentId,
    String parentName,
    List<JobCategoryResponse> subcategories,
    LocalDateTime createdAt
) {
}
