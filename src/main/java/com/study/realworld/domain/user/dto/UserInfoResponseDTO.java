package com.study.realworld.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoResponseDTO {
    private Long id;
    private String email;
    private String username;
    private String bio;
    private String image;

    @Builder
    public UserInfoResponseDTO(Long id, String email, String username, String bio, String image) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.bio = bio;
        this.image = image;
    }

    public static UserInfoResponseDTO of(final User user) {
        return UserInfoResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .bio(user.getBio())
                .image(user.getImage())
                .build();
    }
}
