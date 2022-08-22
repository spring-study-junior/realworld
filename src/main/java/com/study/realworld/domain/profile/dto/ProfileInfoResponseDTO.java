package com.study.realworld.domain.profile.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonTypeName("profile")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileInfoResponseDTO {
    private String username;
    private String bio;
    private String image;
    private Boolean following;

    @Builder
    public ProfileInfoResponseDTO(final String username, final String bio, final String image, final Boolean following) {
        this.username = username;
        this.bio = bio;
        this.image = image;
        this.following = following;
    }
}
