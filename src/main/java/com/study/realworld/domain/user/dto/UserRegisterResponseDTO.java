package com.study.realworld.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonTypeName("user")
public class UserRegisterResponseDTO {
    private final String username;
    private final String email;

    @Builder
    public UserRegisterResponseDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
