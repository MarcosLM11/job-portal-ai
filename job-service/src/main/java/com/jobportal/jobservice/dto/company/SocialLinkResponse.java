package com.jobportal.jobservice.dto.company;

import lombok.Builder;

@Builder
public record SocialLinkResponse(
        SocialPlatform socialPlatform,
        String url
) {

}
