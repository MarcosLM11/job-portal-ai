package com.jobportal.jobservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record JobCategoryRequest(
        @NotBlank(message = "category name is required")
        String name,
        @Size(max = 500, message = "description must be less than or equal to 500 characters")
        String description,
        String iconUrl,
        Long parentId
) {
}
