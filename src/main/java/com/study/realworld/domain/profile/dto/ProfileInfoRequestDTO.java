package com.study.realworld.domain.profile.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileInfoRequestDTO {
    @NotBlank(message = "회원 이름을 입력해주세요.")
    private String username;

    @Builder
    public ProfileInfoRequestDTO(final String username) {
        this.username = username;
    }
}
