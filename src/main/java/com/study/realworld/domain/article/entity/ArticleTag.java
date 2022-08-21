package com.study.realworld.domain.article.entity;

import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

@Entity
@IdClass(ArticleTagId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class ArticleTag {

    @ToString.Exclude
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ToString.Exclude
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder
    public ArticleTag(final Article article, final Tag tag) {
        this.article = article;
        this.tag = tag;
    }

    public void setArticle(final Article article) {
        if (this.article != null) {
            this.article.getArticleTags().remove(this);
        }
        this.article = article;
        this.article.getArticleTags().add(this);
    }

    public void setTag(final Tag tag) {
        if (this.tag != null) {
            this.tag.getArticleTags().remove(this);
        }
        this.tag = tag;
        this.tag.getArticleTags().add(this);
    }
}
