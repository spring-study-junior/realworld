package com.study.realworld.domain.article.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@JsonTypeName("comment")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentSingleResponseDTO {
    private Long id;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String body;
    private ArticleAuthorInfoDTO author;

    @Builder
    public CommentSingleResponseDTO(final Long id, final LocalDateTime createAt, final LocalDateTime updateAt, final String body, final ArticleAuthorInfoDTO author) {
        this.id = id;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.body = body;
        this.author = author;
    }
}
