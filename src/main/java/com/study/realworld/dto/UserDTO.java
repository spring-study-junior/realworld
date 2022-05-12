package com.study.realworld.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.study.realworld.entity.User;
import com.study.realworld.util.StringUtils;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private String username;
    private String email;
    private String password;
    private String bio;
    private String image;

    public UserDTO(User user) {
        this.username = (StringUtils.isEmptyOrNull(user.getUsername())) ? this.username : user.getUsername();
        this.email = (StringUtils.isEmptyOrNull(user.getEmail())) ? this.email : user.getEmail();
        this.password = (StringUtils.isEmptyOrNull(user.getPassword())) ? this.password : user.getPassword();
        this.bio = (StringUtils.isEmptyOrNull(user.getBio())) ? this.bio : user.getBio();
        this.image = (StringUtils.isEmptyOrNull(user.getImage())) ? this.image : user.getImage();
    }
}
