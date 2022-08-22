package com.study.realworld.domain.article.entity;

import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class ArticleTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_tag_id")
    private Long id;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder
    public ArticleTag(final Long id, final Article article, final Tag tag) {
        this.id = id;
        this.article = article;
        this.tag = tag;
    }

    public void setArticle(final Article article) {
        if (this.article != null) {
            article.getArticleTags().remove(this);
        }
        this.article = article;
        article.getArticleTags().add(this);
    }

    public void setTag(final Tag tag) {
        if (this.tag != null) {
            tag.getArticleTags().remove(this);
        }
        this.tag = tag;
        tag.getArticleTags().add(this);
    }
}
