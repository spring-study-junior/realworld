package com.study.realworld.domain.article.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@JsonTypeName("comment")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentCreateRequestDTO {
    @NotBlank(message = "빈 내용을 입력할 수 없습니다.")
    private String body;

    @Builder
    public CommentCreateRequestDTO(String body) {
        this.body = body;
    }
}
