package com.study.realworld.domain.comment.service;

import com.study.realworld.domain.article.Article;
import com.study.realworld.domain.article.repository.ArticleRepository;
import com.study.realworld.domain.comment.Comment;
import com.study.realworld.domain.comment.dto.CommentCreateDto;
import com.study.realworld.domain.comment.dto.CommentResponseDto;
import com.study.realworld.domain.comment.repository.CommentRepository;
import com.study.realworld.domain.member.Member;
import com.study.realworld.domain.member.repository.MemberRepository;
import com.study.realworld.global.exception.NotExistArticleException;
import com.study.realworld.global.exception.NotExistCommentException;
import com.study.realworld.global.exception.NotExistMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    public void create(CommentCreateDto dto, String slug, String loginMemberName) {
        Member login = memberRepository
                .findMemberByName(loginMemberName)
                .orElseThrow(() -> new NotExistMemberException("해당하는 사용자가 없습니다."));

        Article article = articleRepository
                .findArticleBySlug(slug)
                .orElseThrow(() -> new NotExistArticleException("해당하는 게시글이 없습니다."));

        Comment comment = Comment.from(dto, login, article);
        commentRepository.save(comment);// 저장
        article.getComments().add(comment);// 주인인 아닌 곳에 추가
    }

    public List<CommentResponseDto> findAll(String slug, String loginMemberName) {
        Member login = memberRepository
                .findMemberByName(loginMemberName)
                .orElseThrow(() -> new NotExistMemberException("해당 사용자는 없습니다."));

        Article article = articleRepository
                .findArticleBySlug(slug)
                .orElseThrow(() -> new NotExistArticleException("해당하는 게시글이 없습니다."));

        return article.getComments().stream()
                .map(comment -> {
                    CommentResponseDto dto = CommentResponseDto.of(comment);
                    // 로그인한 사용자가 댓글 작성자를 팔로우 했는지 확인하는 과정
                    boolean isFollow = login.getFollowers().contains(comment.getAuthor());
                    dto.getAuthor().setFollowing(isFollow);
                    return dto;
                }).collect(Collectors.toList());
    }

    public void delete(String slug, Long commentId) {
        Article article = articleRepository
                .findArticleBySlug(slug)
                .orElseThrow(() -> new NotExistArticleException("해당하는 게시글이 없습니다."));

        Comment comment = commentRepository
                .findCommentById(commentId)
                .orElseThrow(() -> new NotExistCommentException("해당하는 댓글이 없습니다."));

        article.getComments().remove(comment);
        commentRepository.delete(comment);
    }
}
