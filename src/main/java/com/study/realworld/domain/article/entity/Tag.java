package com.study.realworld.domain.article.entity;

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
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Column(name = "body")
    private String body;

    @ToString.Exclude
    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleTag> articleTags = new ArrayList<>();

    @Builder
    public Tag(final Long id, String body, final List<ArticleTag> articleTags) {
        this.id = id;
        this.body = body;
        this.articleTags = (articleTags == null ? new ArrayList<>() : articleTags);
    }
}
