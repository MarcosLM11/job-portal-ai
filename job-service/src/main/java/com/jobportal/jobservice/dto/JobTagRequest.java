package com.jobportal.jobservice.dto;

import jakarta.validation.constraints.NotBlank;

public record JobTagRequest(
        @NotBlank(message = "Tag name is required")
        String name
) {
}
