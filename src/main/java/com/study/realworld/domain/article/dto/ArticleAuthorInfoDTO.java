package com.study.realworld.domain.article.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleAuthorInfoDTO {
    private String username;
    private String bio;
    private String image;
    private boolean following;

    @Builder
    public ArticleAuthorInfoDTO(final String username, final String bio, final String image, final boolean following) {
        this.username = username;
        this.bio = bio;
        this.image = image;
        this.following = following;
    }
}
