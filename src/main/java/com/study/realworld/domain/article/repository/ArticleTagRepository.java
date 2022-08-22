package com.study.realworld.domain.article.repository;

import com.study.realworld.domain.article.entity.Article;
import com.study.realworld.domain.article.entity.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {
    List<ArticleTag> findAllByArticle(final Article article);
}
