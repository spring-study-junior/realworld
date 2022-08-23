package com.study.realworld.domain.comment.repository;

import com.study.realworld.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommentRepository extends JpaRepository<Comment, Long> {
}
