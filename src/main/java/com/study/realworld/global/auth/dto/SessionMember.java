package com.study.realworld.global.auth.dto;

import com.study.realworld.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SessionMember implements Serializable {
    private String email;
    private String password;
    private String username;
    private String bio;
    private String image;

    public static SessionMember of(Member member) {
        SessionMember dto = new SessionMember();
        dto.setBio(member.getBio());
        dto.setPassword(member.getPassword());
        dto.setEmail(member.getEmail());
        dto.setUsername(member.getName());
        dto.setImage(member.getImg());
        return dto;
    }
}
