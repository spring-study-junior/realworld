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
        this.username = () ? this.username : userDTO.getUsername();
        if(StringUtils.isEmptyOrNull(userDTO.getUsername())){
            throw new NoUserNameEmailPasswordException("필수 정보가 없습니다.");// 예외는 다른 패키지에 따로 생성 필요
        }
        if(StringUtils.isEmptyOrNull(userDTO.getPassword())){
            throw new NoUserNameEmailPasswordException("필수 정보가 없습니다.");// 예외는 다른 패키지에 따로 생성 필요
        }
        if(StringUtils.isEmptyOrNull(userDTO.getEmail())){
            throw new NoUserNameEmailPasswordException("필수 정보가 없습니다.");// 예외는 다른 패키지에 따로 생성 필요
        }
        this.bio = userDTO.getBio();
        this.bio = userDTO.getImage();
    }
}
