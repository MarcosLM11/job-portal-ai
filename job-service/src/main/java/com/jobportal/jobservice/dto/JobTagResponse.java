package com.jobportal.jobservice.dto;

import lombok.Builder;

@Builder
public record JobTagResponse(
        Long id,
        String name,
        String slug
) {
}
