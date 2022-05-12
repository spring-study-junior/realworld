package springstudyjunior.realworld.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import springstudyjunior.realworld.entity.User;

@Getter
@NoArgsConstructor
public class NewUserRequestDto {
    private String email;
    private String username;
    private String password;

    @Builder
    public NewUserRequestDto(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public User toEntity() {
        return User.builder()
            .email(email)
            .username(username)
            .password(password)
            .build();
    }


}
