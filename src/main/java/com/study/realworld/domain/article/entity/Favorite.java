package com.study.realworld.domain.article.entity;

import com.study.realworld.domain.user.entity.User;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Favorite(final Long id, final Article article, final User user) {
        this.id = id;
        this.article = article;
        this.user = user;
    }

    public void setArticle(final Article article) {
        if (this.article != null) {
            article.getFavorites().remove(this);
        }
        this.article = article;
        article.getFavorites().add(this);
    }

    public void setUser(final User user) {
        if (this.user != null) {
            user.getFavorites().remove(this);
        }
        this.user = user;
        user.getFavorites().add(this);
    }
}
