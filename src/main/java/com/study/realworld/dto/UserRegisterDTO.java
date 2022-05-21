package com.study.realworld.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {
    @NotBlank(message = "빈 값을 입력할 수 없습니다.")
    @Email(message = "옳바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "빈 값을 입력할 수 없습니다.")
    private String username;

    @NotBlank(message = "빈 값을 입력할 수 없습니다.")
    private String password;

    public User toEntityWithPasswordEncode(PasswordEncoder encoder) {
        this.password = encoder.encode(password);
        return new User(email, username, password);
    }
}
