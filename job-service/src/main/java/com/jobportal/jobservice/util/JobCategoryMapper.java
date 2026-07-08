package com.jobportal.jobservice.util;

import com.jobportal.jobservice.domain.JobCategory;
import com.jobportal.jobservice.dto.JobCategoryResponse;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class JobCategoryMapper {

    public static JobCategoryResponse  toDto(JobCategory category, Boolean includeChildren) {

        var subCategories = new ArrayList<JobCategoryResponse>();

        if (Boolean.TRUE.equals(includeChildren))
            subCategories = category.getSubCategories().stream()
                .map(subCategory -> toDto(subCategory, false))
                    .collect(Collectors.toCollection(ArrayList::new));

        return JobCategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .iconUrl(category.getIconUrl())
                .active(category.getActive())
                .parentId(category.getParent() != null ? category.getParent().getId() : null)
                .parentName(category.getParent() != null ? category.getParent().getName() : null)
                .subcategories(subCategories)
                .createdAt(category.getCreatedAt())
                .build();
    }
}
