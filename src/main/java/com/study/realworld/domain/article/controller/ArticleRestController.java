package com.study.realworld.domain.article.controller;

import com.study.realworld.domain.article.dto.ArticleSingleResponseDTO;
import com.study.realworld.domain.article.dto.ArticleCreateRequestDTO;
import com.study.realworld.domain.article.dto.ArticleFindBySlugRequestDTO;
import com.study.realworld.domain.article.dto.ArticleFindAllResponseDTO;
import com.study.realworld.domain.article.dto.ArticleFindAllRequestDTO;
import com.study.realworld.domain.article.dto.ArticleFindAllFeedRequestDTO;
import com.study.realworld.domain.article.dto.ArticleUpdateRequestDTO;
import com.study.realworld.domain.article.dto.CommentIdRequestDTO;
import com.study.realworld.domain.article.dto.CommentSingleResponseDTO;
import com.study.realworld.domain.article.dto.CommentMultipleResponseDTO;
import com.study.realworld.domain.article.dto.CommentCreateRequestDTO;
import com.study.realworld.domain.article.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.validation.Valid;

@RestController
@RequestMapping("/articles")
@Validated
@Slf4j
public class ArticleRestController {

    private final ArticleService articleService;

    public ArticleRestController(final ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ArticleSingleResponseDTO create(@Valid @RequestBody ArticleCreateRequestDTO requestDTO) {
        return articleService.save(requestDTO);
    }

    @GetMapping("/{slug}")
    public ArticleSingleResponseDTO findBySlug(@PathVariable("slug") ArticleFindBySlugRequestDTO requestDTO) {
        return articleService.findBySlug(requestDTO);
    }

    @GetMapping
    public ArticleFindAllResponseDTO findAllWithParam(ArticleFindAllRequestDTO requestDTO) {
        return articleService.findAll(requestDTO);
    }

    @GetMapping("/feed")
    public ArticleFindAllResponseDTO findAllFeed(ArticleFindAllFeedRequestDTO requestDTO) {
        return articleService.findAllFeed(requestDTO);
    }

    @PutMapping("/{slug}")
    public ArticleSingleResponseDTO updateArticle(@PathVariable("slug") ArticleFindBySlugRequestDTO slugRequestDTO, @RequestBody ArticleUpdateRequestDTO updateRequestDTO) {
        return articleService.updateArticle(slugRequestDTO, updateRequestDTO);
    }

    @DeleteMapping("/{slug}")
    public String deleteArticle(@PathVariable("slug") ArticleFindBySlugRequestDTO slugRequestDTO) {
        articleService.deleteArticle(slugRequestDTO);
        return "ok";
    }

    @PostMapping("/{slug}/comments")
    public CommentSingleResponseDTO addCommentsToAnArticle(@PathVariable("slug") ArticleFindBySlugRequestDTO slugRequestDTO, @RequestBody CommentCreateRequestDTO commentRequestDTO) {
        return articleService.addCommentsToAnArticle(slugRequestDTO, commentRequestDTO);
    }

    @GetMapping("/{slug}/comments")
    public CommentMultipleResponseDTO getCommentFromAnArticle(@PathVariable("slug") ArticleFindBySlugRequestDTO slugRequestDTO) {
        return articleService.getCommentFromAnArticle(slugRequestDTO);
    }

    @DeleteMapping("/{slug}/comments/{id}")
    public String deleteComment(@PathVariable("slug") ArticleFindBySlugRequestDTO slugRequestDTO, @Valid @PathVariable("id") CommentIdRequestDTO commentIdRequestDTO) {
        articleService.deleteComment(slugRequestDTO, commentIdRequestDTO);
        return "ok";
    }
}
