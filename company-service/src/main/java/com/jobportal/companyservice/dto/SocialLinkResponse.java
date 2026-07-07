package com.jobportal.companyservice.dto;

import com.jobportal.companyservice.domain.SocialPlatform;
import lombok.Builder;

@Builder
public record SocialLinkResponse(
        SocialPlatform socialPlatform,
        String url
) {

}
