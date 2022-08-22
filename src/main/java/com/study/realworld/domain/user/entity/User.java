package com.study.realworld.domain.user.entity;

import com.study.realworld.domain.article.entity.Article;
import com.study.realworld.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @ToString.Exclude
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "bio")
    private String bio;

    @Column(name = "image")
    private String image;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ToString.Exclude
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles = new ArrayList<>();

    @Builder
    public User(final Long id, final String email, final String username, final String password, final String bio, final String image, final Comment comment, final List<Article> articles) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.image = image;
        this.comment = comment;
        this.articles = (articles == null ? new ArrayList<>() : articles);
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setBio(final String bio) {
        this.bio = bio;
    }

    public void setImage(final String image) {
        this.image = image;
    }
}
