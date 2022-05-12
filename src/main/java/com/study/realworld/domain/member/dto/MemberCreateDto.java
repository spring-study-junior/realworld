package com.study.realworld.domain.member.dto;

import com.study.realworld.domain.member.Member;
import com.study.realworld.domain.member.Role;
import lombok.Builder;
import lombok.Data;

@Data
public class MemberCreateDto {
    private String username;
    private String password;
    private String email;
    private String bio;
    private String image;
    private Role role;

    @Builder
    public MemberCreateDto(String username, String password, String email, String bio, String image) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.bio = bio;
        this.image = image;
    }

    public static Member from(MemberCreateDto dto){
        return Member.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .bio(dto.getBio())
                .image(dto.getImage())
                .role(Role.USER)
                .build();
    }
}
