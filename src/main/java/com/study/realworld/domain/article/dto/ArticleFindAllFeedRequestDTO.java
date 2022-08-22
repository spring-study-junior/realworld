package com.study.realworld.domain.article.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleFindAllFeedRequestDTO {
    private Integer limit = 20;
    private Integer offset = 0;

    @Builder
    public ArticleFindAllFeedRequestDTO(final Integer limit, final Integer offset) {
        this.limit = (limit == null ? 20 : Math.max(limit, 20));
        this.offset = (offset == null ? 0 : Math.max(offset, 0));
    }
}
