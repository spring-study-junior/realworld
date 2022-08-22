package com.study.realworld.domain.article.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@JsonTypeName("article")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleCreateRequestDTO {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "설명을 입력해주세요.")
    private String description;

    @NotBlank(message = "본문을 입력해주세요.")
    private String body;

    private List<String> tagList = new ArrayList<>();

    @Builder
    public ArticleCreateRequestDTO(final String title, final String description, final String body, final List<String> tagList) {
        this.title = title;
        this.description = description;
        this.body = body;
        this.tagList = (tagList == null ? new ArrayList<>() : tagList);
    }
}
