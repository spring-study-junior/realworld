package com.study.realworld.domain.article.repository;

import com.study.realworld.domain.article.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("SELECT DISTINCT t.body FROM Tag t")
    List<String> findAllDistinctBody();
}
