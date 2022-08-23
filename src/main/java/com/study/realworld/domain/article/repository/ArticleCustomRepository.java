package com.study.realworld.domain.article.repository;

import com.study.realworld.domain.article.dto.ArticleFindAllFeedRequestDTO;
import com.study.realworld.domain.article.dto.ArticleFindAllRequestDTO;
import com.study.realworld.domain.article.dto.ArticleFindBySlugRequestDTO;
import com.study.realworld.domain.article.dto.CommentIdRequestDTO;
import com.study.realworld.domain.article.entity.Article;
import com.study.realworld.domain.comment.entity.Comment;
import com.study.realworld.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ArticleCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public ArticleCustomRepository(final EntityManager em) {
        this.em = em;
    }

    public List<Article> findAll(final ArticleFindAllRequestDTO requestDTO) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT a FROM Article a");
        sb.append(" join fetch a.articleTags");
        sb.append(" WHERE 1 = 1");
        if (requestDTO.getAuthor() != null) {
            sb.append(" AND a.author.username = :author");
        }
        sb.append(" ORDER BY a.createAt desc, a.updateAt desc");
        TypedQuery<Article> query = em.createQuery(sb.toString(), Article.class);
        if (requestDTO.getAuthor() != null) {
            query.setParameter("author", requestDTO.getAuthor());
        }
        return query
                .setFirstResult(requestDTO.getOffset())
                .setMaxResults(requestDTO.getLimit())
                .getResultList();
    }

    public List<Article> findAllFeed(final ArticleFindAllFeedRequestDTO requestDTO, final List<User> followingUsers) {
        String jpql = " SELECT a FROM Article a" +
                " join fetch a.articleTags" +
                " WHERE a.author in :followingUsers" +
                " ORDER BY a.createAt desc, a.updateAt desc";
        return em.createQuery(jpql, Article.class)
                .setParameter("followingUsers", followingUsers)
                .setFirstResult(requestDTO.getOffset())
                .setMaxResults(requestDTO.getLimit())
                .getResultList();
    }

    public List<Comment> findByCommentIdAndArticleSlug(final ArticleFindBySlugRequestDTO slugRequestDTO, final CommentIdRequestDTO commentIdRequestDTO) {
        String jpql = "SELECT c FROM Comment c" +
                " WHERE c.article.slug = :slug" +
                " AND c.id = :id";
        return em.createQuery(jpql, Comment.class)
                .setParameter("id", commentIdRequestDTO.getId())
                .setParameter("slug", slugRequestDTO.getSlug().toLowerCase())
                .setMaxResults(1)
                .getResultList();
    }

    public List<Article> findBySlugWithArticleTag(final String slug) {
        String jpql = "SELECT a FROM Article a" +
                " join fetch a.articleTags" +
                " WHERE a.slug = :slug";
        return em.createQuery(jpql, Article.class)
                .setParameter("slug", slug)
                .setMaxResults(1)
                .getResultList();
    }
}
