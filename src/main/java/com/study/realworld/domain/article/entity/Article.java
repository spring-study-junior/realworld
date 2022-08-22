package com.study.realworld.domain.article.entity;

import com.study.realworld.domain.comment.entity.Comment;
import com.study.realworld.domain.user.entity.User;
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
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.study.realworld.domain.common.util.StringUtils.generateSlug;

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

    @ToString.Exclude
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite> favorites = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleTag> articleTags = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @Builder
    public Article(final Long id, final String slug, final String title, final String description, final String body, final LocalDateTime createAt, final LocalDateTime updateAt, final List<Favorite> favorites, final List<ArticleTag> articleTags, final List<Comment> comments, final User author) {
        this.id = id;
        this.slug = generateSlug(title);
        this.title = title;
        this.description = description;
        this.body = body;
        this.createAt = (createAt == null ? LocalDateTime.now() : createAt);
        this.updateAt = updateAt;
        this.favorites = (favorites == null ? new ArrayList<>() : favorites);
        this.articleTags = (articleTags == null ? new ArrayList<>() : articleTags);
        this.comments = (comments == null ? new ArrayList<>() : comments);
        this.author = author;
    }

    public void setAuthor(final User author) {
        if (this.author != null) {
            author.getArticles().remove(this);
        }
        this.author = author;
        author.getArticles().add(this);
    }

    public void setTitle(final String title) {
        this.title = title;
        this.slug = generateSlug(title);
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setBody(final String body) {
        this.body = body;
    }
}
