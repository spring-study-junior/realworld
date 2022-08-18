package com.study.realworld.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateRequestDTO {
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "옳바른 이메일 형식이 아닙니다.")
    private String email;

    private String username;

    private String password;

    private String bio;

    private String image;

    @Builder
    public UserUpdateRequestDTO(String email, String username, String password, String bio, String image) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.image = image;
    }
}
