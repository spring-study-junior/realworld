package com.study.realworld.domain.article.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagFindAllResponseDTO {

    private List<String> tags = new ArrayList<>();

    @Builder
    public TagFindAllResponseDTO(final List<String> tags) {
        this.tags = (tags == null ? new ArrayList<>() : tags);
    }
}
