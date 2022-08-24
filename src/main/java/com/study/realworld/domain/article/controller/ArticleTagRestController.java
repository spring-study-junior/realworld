package com.study.realworld.domain.article.controller;

import com.study.realworld.domain.article.dto.TagFindAllResponseDTO;
import com.study.realworld.domain.article.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
@Validated
@Slf4j
public class ArticleTagRestController {

    private final TagService tagService;

    public ArticleTagRestController(final TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public TagFindAllResponseDTO findAll() {
        return tagService.findAll();
    }
}
