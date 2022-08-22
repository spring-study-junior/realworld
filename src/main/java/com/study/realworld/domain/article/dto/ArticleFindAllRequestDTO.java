package com.study.realworld.domain.article.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleFindAllRequestDTO {
    private String tag;
    private String author;
    private String favorited;
    private Integer limit = 20;
    private Integer offset = 0;

    @Builder
    public ArticleFindAllRequestDTO(final String tag, final String author, final String favorited, final Integer limit, final Integer offset) {
        this.tag = tag;
        this.author = author;
        this.favorited = favorited;
        this.limit = (limit == null ? 20 : Math.max(limit, 20));
        this.offset = (offset == null ? 0 : Math.max(offset, 0));
    }
}
