package com.study.realworld.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRegisterRequestDTO {
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "옳바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "이름을 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    public User toEntityWithPasswordEncode(PasswordEncoder encoder) {
        this.password = encoder.encode(password);
        return new User(email, username, password);
    }

    @Builder
    public UserRegisterRequestDTO(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
