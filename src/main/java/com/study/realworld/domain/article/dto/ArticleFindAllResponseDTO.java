package com.study.realworld.domain.article.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleFindAllResponseDTO {
    private List<ArticleSingleResponseDTO> articles = new ArrayList<>();
    private Long articlesCount;

    @Builder
    public ArticleFindAllResponseDTO(final List<ArticleSingleResponseDTO> articles, final Long articlesCount) {
        this.articles = (articles == null ? new ArrayList<>() : articles);
        this.articlesCount = articlesCount;
    }
}
