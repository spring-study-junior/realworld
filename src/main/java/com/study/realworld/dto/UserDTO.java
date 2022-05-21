package com.study.realworld.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.study.realworld.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private String bio;
    private String image;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.bio = user.getBio();
        this.image = user.getImage();
    }
}
