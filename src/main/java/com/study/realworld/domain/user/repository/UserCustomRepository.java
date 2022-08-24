package com.study.realworld.domain.user.repository;

import com.study.realworld.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public UserCustomRepository(EntityManager em) {
        this.em = em;
    }

    public List<User> findByIdWithFollows(final Long id) {
        String jpql = "SELECT a FROM User a" +
                " JOIN FETCH a.follows" +
                " WHERE a.id = :id";
        return em.createQuery(jpql, User.class)
                .setParameter("id", id)
                .setMaxResults(1)
                .getResultList();
    }
}
