package com.study.realworld.entity;

import lombok.Builder;
import lombok.Data;
import javax.persistence.*;

@Entity
@Builder
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 50, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String bio;

    private String image;

    protected User() { /*empty*/ }

    public User(Long id, String email, String username, String password, String bio, String image) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.image = image;
    }

    public User(final String email, final String username, final String password) {
        this(null, email, username, password, null, null);
    }
}
