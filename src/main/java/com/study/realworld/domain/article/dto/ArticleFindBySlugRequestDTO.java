package com.study.realworld.domain.article.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleFindBySlugRequestDTO {
    @NotBlank(message = "slug를 입력해주세요.")
    private String slug;

    @Builder
    public ArticleFindBySlugRequestDTO(final String slug) {
        this.slug = slug;
    }
}
