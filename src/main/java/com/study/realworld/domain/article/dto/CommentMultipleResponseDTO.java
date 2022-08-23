package com.study.realworld.domain.article.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentMultipleResponseDTO {
    private List<CommentSingleResponseDTO> comments = new ArrayList<>();

    @Builder
    public CommentMultipleResponseDTO(final List<CommentSingleResponseDTO> comments) {
        this.comments = (comments == null ? new ArrayList<>() : comments);
    }
}
