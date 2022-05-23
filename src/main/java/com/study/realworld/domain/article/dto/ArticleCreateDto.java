package com.study.realworld.domain.article.dto;

import com.study.realworld.domain.article.Article;
import lombok.Data;

import java.util.List;
import java.util.Locale;

@Data
public class ArticleCreateDto {
    private String title;
    private String description;
    private String body;
    private List<String> tagList;
    private final static String BLANK = " ", UNDER_BAR = "_";

    public static Article from(ArticleCreateDto dto){

        return Article.builder()
                .slug(dto.getTitle().toLowerCase(Locale.ROOT).replace(BLANK,UNDER_BAR))
                .title(dto.getTitle())
                .description(dto.getDescription())
                .body(dto.getBody())
                .build();
    }
}
