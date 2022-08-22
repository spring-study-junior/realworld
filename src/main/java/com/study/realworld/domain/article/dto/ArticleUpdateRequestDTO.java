package com.study.realworld.domain.article.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonTypeName("article")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleUpdateRequestDTO {
    private String title;
    private String description;
    private String body;

    @Builder
    public ArticleUpdateRequestDTO(final String title, final String description, final String body) {
        this.title = title;
        this.description = description;
        this.body = body;
    }
}
