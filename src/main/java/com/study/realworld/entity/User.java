package com.study.realworld.entity;

import com.study.realworld.dto.UserDTO;
import com.study.realworld.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String email;

    @Column(length = 50)
    private String username;

    @Column(length = 50)
    private String password;

    private String bio;

    private String image;

    public User(UserDTO userDTO) {
        this.username = (StringUtils.isEmptyOrNull(userDTO.getUsername())) ? this.username : userDTO.getUsername();
        this.email = (StringUtils.isEmptyOrNull(userDTO.getEmail())) ? this.email : userDTO.getEmail();
        this.password = (StringUtils.isEmptyOrNull(userDTO.getPassword())) ? this.password : userDTO.getPassword();
        this.bio = (StringUtils.isEmptyOrNull(userDTO.getBio())) ? this.bio : userDTO.getBio();
        this.image = (StringUtils.isEmptyOrNull(userDTO.getImage())) ? this.image : userDTO.getImage();
    }
}
