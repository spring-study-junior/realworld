package com.study.realworld.domain.member;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@Entity
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    private String email;

    private String bio;
    private String image;

    @Enumerated(EnumType.STRING)
    private Role role;


    @Builder
    public Member(String username, String password, String email, String bio, String image, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.bio = bio;
        this.image = image;
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
