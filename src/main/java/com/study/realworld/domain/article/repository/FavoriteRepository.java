package com.study.realworld.domain.article.repository;

import com.study.realworld.domain.article.entity.Article;
import com.study.realworld.domain.article.entity.Favorite;
import com.study.realworld.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    boolean existsByArticleAndUser(final Article article, final User user);
    void deleteByArticleAndUser(final Article article, final User user);
}
