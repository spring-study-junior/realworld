package com.study.realworld.domain.article.entity;

import com.study.realworld.domain.comment.entity.Comment;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @Column(name = "slug")
    private String slug;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "body")
    private String body;

    @Column(name = "create_at")
    @CreatedDate
    private LocalDateTime createAt;

    @Column(name = "update_at")
    @LastModifiedDate
    private LocalDateTime updateAt;

    @Column(name = "favorited")
    private Boolean favorited;

    @Column(name = "favorites_count")
    private Integer favoritesCount;

    @ToString.Exclude
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleTag> articleTags = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Article(final Long id, final String slug, final String title, final String description, final String body, final LocalDateTime createAt, final LocalDateTime updateAt, final Boolean favorited, final Integer favoritesCount, final List<ArticleTag> articleTags, final List<Comment> comments) {
        this.id = id;
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.body = body;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.favorited = favorited;
        this.favoritesCount = favoritesCount;
        this.articleTags = (articleTags == null ? new ArrayList<>() : articleTags);
        this.comments = (comments == null ? new ArrayList<>() : comments);
    }
}
