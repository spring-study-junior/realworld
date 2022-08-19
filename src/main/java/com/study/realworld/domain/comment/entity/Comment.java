package com.study.realworld.domain.comment.entity;

import com.study.realworld.domain.article.entity.Article;
import com.study.realworld.domain.user.entity.User;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.AccessLevel;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "create_at")
    @CreatedDate
    private LocalDateTime createAt;

    @Column(name = "update_at")
    @LastModifiedDate
    private LocalDateTime updateAt;

    @Column(name = "body")
    private String body;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "comment")
    private User user;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @Builder
    public Comment(final Long id, final LocalDateTime createAt, final LocalDateTime updateAt, final String body, final User user, final Article article) {
        this.id = id;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.body = body;
        this.user = user;
        this.article = article;
    }

    public void setArticle(Article article) {
        if (this.article != null) {
            this.article.getComments().remove(this);
        }
        this.article = article;
        this.article.getComments().add(this);
    }
}
