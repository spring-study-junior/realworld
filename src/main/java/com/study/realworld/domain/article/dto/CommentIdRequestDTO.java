package com.study.realworld.domain.article.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentIdRequestDTO {
    @NotNull(message = "id 값을 입력해주세요.")
    @Min(value = 0, message = "제한된 범위 밖입니다.")
    @Max(value = Long.MAX_VALUE, message = "제한된 범위 밖입니다.")
    private Long id;

    @Builder
    public CommentIdRequestDTO(final Long id) {
        this.id = id;
    }

    public CommentIdRequestDTO(final String idStr) {
        try {
            this.id = Long.parseLong(idStr);
        } catch (Exception e) {
            throw new IllegalArgumentException("숫자만 입력 가능합니다.");
        }
    }
}
