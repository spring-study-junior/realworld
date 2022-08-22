package com.study.realworld.domain.article.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.domain.user.dto.UserInfoResponseDTO;
import com.study.realworld.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@JsonTypeName("article")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleSingleResponseDTO {
    private String slug;
    private String title;
    private String description;
    private String body;
    private List<String> tagList = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean favorited;
    private Long favoritesCount;
    private UserInfoResponseDTO author;

    @Builder
    public ArticleSingleResponseDTO(final String slug, final String title, final String description, final String body, final List<String> tagList, final LocalDateTime createdAt, final LocalDateTime updatedAt, final boolean favorited, final Long favoritesCount, final User author) {
        this.slug = slug;
        this.title = title;
        this.description = description;
        this.body = body;
        this.tagList = (tagList == null ? new ArrayList<>() : tagList);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.favorited = favorited;
        this.favoritesCount = (favoritesCount == null ? 0 : favoritesCount);
        this.author = UserInfoResponseDTO.of(author);
    }
}
