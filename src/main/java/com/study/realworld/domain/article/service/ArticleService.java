package com.study.realworld.domain.article.service;

import com.study.realworld.domain.article.dto.ArticleSingleResponseDTO;
import com.study.realworld.domain.article.dto.ArticleCreateRequestDTO;
import com.study.realworld.domain.article.dto.ArticleFindBySlugRequestDTO;
import com.study.realworld.domain.article.dto.ArticleFindAllResponseDTO;
import com.study.realworld.domain.article.dto.ArticleFindAllRequestDTO;
import com.study.realworld.domain.article.dto.ArticleFindAllFeedRequestDTO;
import com.study.realworld.domain.article.dto.ArticleUpdateRequestDTO;
import com.study.realworld.domain.article.dto.CommentSingleResponseDTO;
import com.study.realworld.domain.article.dto.ArticleAuthorInfoDTO;
import com.study.realworld.domain.article.dto.CommentCreateRequestDTO;
import com.study.realworld.domain.article.dto.CommentMultipleResponseDTO;
import com.study.realworld.domain.article.dto.CommentIdRequestDTO;
import com.study.realworld.domain.article.entity.Article;
import com.study.realworld.domain.article.entity.ArticleTag;
import com.study.realworld.domain.article.entity.Favorite;
import com.study.realworld.domain.article.entity.Tag;
import com.study.realworld.domain.article.repository.ArticleRepository;
import com.study.realworld.domain.article.repository.ArticleTagRepository;
import com.study.realworld.domain.article.repository.ArticleCustomRepository;
import com.study.realworld.domain.article.repository.TagRepository;
import com.study.realworld.domain.article.repository.FavoriteRepository;
import com.study.realworld.domain.comment.entity.Comment;
import com.study.realworld.domain.comment.repository.CommentRepository;
import com.study.realworld.domain.profile.entity.Follow;
import com.study.realworld.domain.user.entity.User;
import com.study.realworld.domain.user.repository.UserCustomRepository;
import com.study.realworld.domain.user.repository.UserRepository;
import com.study.realworld.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final ArticleTagRepository articleTagRepository;
    private final ArticleCustomRepository articleCustomRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;
    private final UserCustomRepository userCustomRepository;
    private final FavoriteRepository favoriteRepository;

    @Autowired
    public ArticleService(final UserRepository userRepository, final ArticleRepository articleRepository, final ArticleTagRepository articleTagRepository, final ArticleCustomRepository articleCustomRepository, final TagRepository tagRepository, final CommentRepository commentRepository, final UserCustomRepository userCustomRepository, final FavoriteRepository favoriteRepository) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.articleTagRepository = articleTagRepository;
        this.articleCustomRepository = articleCustomRepository;
        this.tagRepository = tagRepository;
        this.commentRepository = commentRepository;
        this.userCustomRepository = userCustomRepository;
        this.favoriteRepository = favoriteRepository;
    }

    @Transactional
    public ArticleSingleResponseDTO save(final ArticleCreateRequestDTO requestDTO) {
        Long currentMemberId = SecurityUtils.getCurrentMemberId().orElseThrow(() -> new IllegalArgumentException("인증 정보가 없습니다."));
        User author = userRepository.findById(currentMemberId).orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다."));
        Article article = articleRepository.save(Article.builder()
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .body(requestDTO.getBody())
                .author(author)
                .build());
        article.setAuthor(author);
        for (final String body : requestDTO.getTagList()) {
            Tag tag = Tag.builder()
                    .body(body)
                    .build();
            tagRepository.save(tag);
            ArticleTag articleTag = ArticleTag.builder()
                    .article(article)
                    .tag(tag)
                    .build();
            articleTagRepository.save(articleTag);
            articleTag.setTag(tag);
            articleTag.setArticle(article);
        }
        return ArticleSingleResponseDTO.builder()
                .slug(article.getSlug())
                .title(requestDTO.getTitle())
                .description(requestDTO.getDescription())
                .body(article.getBody())
                .tagList(requestDTO.getTagList())
                .createdAt(article.getCreateAt())
                .updatedAt(article.getUpdateAt())
                .favorited(false)
                .favoritesCount(0L)
                .author(ArticleAuthorInfoDTO.builder()
                        .username(article.getAuthor().getUsername())
                        .bio(article.getAuthor().getBio())
                        .image(article.getAuthor().getImage())
                        .following(false)
                        .build())
                .build();
    }

    @Transactional(readOnly = true)
    public ArticleSingleResponseDTO findBySlug(final ArticleFindBySlugRequestDTO requestDTO) {
        Long currentMemberId = SecurityUtils.getCurrentMemberId().orElse(null);
        User me = (currentMemberId == null ? null : userCustomRepository.findByIdWithFollows(currentMemberId).stream().findFirst().orElse(null));
        Article article = articleCustomRepository.findBySlugWithArticleTag(requestDTO.getSlug().toLowerCase()).stream().findFirst().orElseThrow(() -> new IllegalArgumentException("게시글 정보가 없습니다."));
        List<String> tagNames = article.getArticleTags().stream().map(ArticleTag::getTag).map(Tag::getBody).collect(Collectors.toList());
        return getArticleSingleResponseDTO(me, article, tagNames);
    }

    @Transactional(readOnly = true)
    public ArticleFindAllResponseDTO findAll(final ArticleFindAllRequestDTO requestDTO) {
        Long currentMemberId = SecurityUtils.getCurrentMemberId().orElse(null);
        User me = (currentMemberId == null ? null : userCustomRepository.findByIdWithFollows(currentMemberId).stream().findFirst().orElse(null));
        List<Article> articles = articleCustomRepository.findAll(requestDTO);
        return getArticleFindAllResponseDTO(me, articles);
    }

    @Transactional(readOnly = true)
    public ArticleFindAllResponseDTO findAllFeed(final ArticleFindAllFeedRequestDTO requestDTO) {
        Long currentMemberId = SecurityUtils.getCurrentMemberId().orElseThrow(() -> new IllegalArgumentException("인증 정보가 없습니다."));
        User me = userCustomRepository.findByIdWithFollows(currentMemberId).stream().findFirst().orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다."));
        List<User> followUsers = me.getFollows().stream().map(Follow::getFromUser).collect(Collectors.toList());
        followUsers.add(me);
        List<Article> articles = articleCustomRepository.findAllFeed(requestDTO, followUsers);
        return getArticleFindAllResponseDTO(me, articles);
    }

    @Transactional
    public ArticleSingleResponseDTO updateArticle(final ArticleFindBySlugRequestDTO slugRequestDTO, final ArticleUpdateRequestDTO updateRequestDTO) {
        Long currentMemberId = SecurityUtils.getCurrentMemberId().orElseThrow(() -> new IllegalArgumentException("인증 정보가 없습니다."));
        User me = userCustomRepository.findByIdWithFollows(currentMemberId).stream().findFirst().orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다."));
        Article article = articleRepository.findBySlug(slugRequestDTO.getSlug().toLowerCase()).orElseThrow(() -> new IllegalArgumentException("게시글 정보가 없습니다."));
        List<String> tagNames = article.getArticleTags().stream().map(ArticleTag::getTag).map(Tag::getBody).collect(Collectors.toList());
        if (updateRequestDTO.getTitle() != null) {
            article.setTitle(updateRequestDTO.getTitle());
        }
        if (updateRequestDTO.getDescription() != null) {
            article.setDescription(updateRequestDTO.getDescription());
        }
        if (updateRequestDTO.getBody() != null) {
            article.setBody(updateRequestDTO.getBody());
        }
        return getArticleSingleResponseDTO(me, article, tagNames);
    }

    @Transactional
    public void deleteArticle(final ArticleFindBySlugRequestDTO requestDTO) {
        Long currentMemberId = SecurityUtils.getCurrentMemberId().orElseThrow(() -> new IllegalArgumentException("인증 정보가 없습니다."));
        userRepository.findById(currentMemberId).orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다"));
        Article article = articleRepository.findBySlug(requestDTO.getSlug().toLowerCase()).orElseThrow(() -> new IllegalArgumentException("게시글 정보가 없습니다."));
        article.getArticleTags().stream().map(ArticleTag::getTag).forEach(tagRepository::delete);
        articleRepository.delete(article);
    }

    @Transactional
    public CommentSingleResponseDTO addCommentsToAnArticle(final ArticleFindBySlugRequestDTO slugRequestDTO, final CommentCreateRequestDTO addCommentRequestDTO) {
        Long currentMemberId = SecurityUtils.getCurrentMemberId().orElseThrow(() -> new IllegalArgumentException("인증 정보가 없습니다."));
        User me = userCustomRepository.findByIdWithFollows(currentMemberId).stream().findFirst().orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다."));
        Article article = articleRepository.findBySlug(slugRequestDTO.getSlug().toLowerCase()).orElseThrow(() -> new IllegalArgumentException("게시글 정보가 없습니다."));
        Comment comment = commentRepository.save(Comment.builder()
                .body(addCommentRequestDTO.getBody())
                .build());
        comment.setArticle(article);
        return getCommentSingleResponseDTO(comment, article, me);
    }

    @Transactional(readOnly = true)
    public CommentMultipleResponseDTO getCommentFromAnArticle(final ArticleFindBySlugRequestDTO slugRequestDTO) {
        Long currentMemberId = SecurityUtils.getCurrentMemberId().orElse(null);
        User me = (currentMemberId == null ? null : userCustomRepository.findByIdWithFollows(currentMemberId).stream().findFirst().orElse(null));
        Article article = articleRepository.findBySlug(slugRequestDTO.getSlug().toLowerCase()).orElseThrow(() -> new IllegalArgumentException("게시글 정보가 없습니다."));
        List<CommentSingleResponseDTO> response = new ArrayList<>();
        for (final Comment comment : article.getComments()) {
            response.add(getCommentSingleResponseDTO(comment, article, me));
        }
        return CommentMultipleResponseDTO.builder()
                .comments(response)
                .build();
    }

    @Transactional
    public void deleteComment(final ArticleFindBySlugRequestDTO slugRequestDTO, final CommentIdRequestDTO commentIdRequestDTO) {
        Long currentMemberId = SecurityUtils.getCurrentMemberId().orElseThrow(() -> new IllegalArgumentException("인증 정보가 없습니다."));
        userRepository.findById(currentMemberId).orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다"));
        Comment comment = articleCustomRepository.findByCommentIdAndArticleSlug(slugRequestDTO, commentIdRequestDTO).stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글에 속한 댓글 정보가 없습니다."));
        commentRepository.delete(comment);
    }

    @Transactional
    public ArticleSingleResponseDTO favoriteArticle(final ArticleFindBySlugRequestDTO requestDTO) {
        Long currentMemberId = SecurityUtils.getCurrentMemberId().orElseThrow(() -> new IllegalArgumentException("인증 정보가 없습니다."));
        User me = userCustomRepository.findByIdWithFollows(currentMemberId).stream().findFirst().orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다."));
        Article article = articleRepository.findBySlug(requestDTO.getSlug().toLowerCase()).orElseThrow(() -> new IllegalArgumentException("게시글 정보가 없습니다."));
        List<String> tagNames = article.getArticleTags().stream().map(ArticleTag::getTag).map(Tag::getBody).collect(Collectors.toList());
        boolean isExists = favoriteRepository.existsByArticleAndUser(article, me);
        if (!isExists) {
            Favorite favorite = Favorite.builder()
                    .article(article)
                    .user(me)
                    .build();
            favoriteRepository.save(favorite);
            favorite.setArticle(article);
            favorite.setUser(me);
        }
        return getArticleSingleResponseDTO(me, article, tagNames);
    }

    @Transactional
    public ArticleSingleResponseDTO unFavoriteArticle(final ArticleFindBySlugRequestDTO requestDTO) {
        Long currentMemberId = SecurityUtils.getCurrentMemberId().orElseThrow(() -> new IllegalArgumentException("인증 정보가 없습니다."));
        User me = userCustomRepository.findByIdWithFollows(currentMemberId).stream().findFirst().orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다."));
        Article article = articleRepository.findBySlug(requestDTO.getSlug().toLowerCase()).orElseThrow(() -> new IllegalArgumentException("게시글 정보가 없습니다."));
        List<String> tagNames = article.getArticleTags().stream().map(ArticleTag::getTag).map(Tag::getBody).collect(Collectors.toList());
        favoriteRepository.deleteByArticleAndUser(article, me);
        return getArticleSingleResponseDTO(me, article, tagNames);
    }

    private ArticleFindAllResponseDTO getArticleFindAllResponseDTO(final User me, final List<Article> articles) {
        List<ArticleSingleResponseDTO> response = new ArrayList<>();
        for (final Article article : articles) {
            response.add(ArticleSingleResponseDTO.builder()
                    .slug(article.getSlug())
                    .title(article.getTitle())
                    .description(article.getDescription())
                    .body(article.getBody())
                    .tagList(article.getArticleTags().stream().map(ArticleTag::getTag).map(Tag::getBody).collect(Collectors.toList()))
                    .createdAt(article.getCreateAt())
                    .updatedAt(article.getUpdateAt())
                    .favorited(me != null && article.getFavorites().stream().map(Favorite::getUser).collect(Collectors.toList()).contains(me))
                    .favoritesCount(article.getFavorites().stream().map(Favorite::getUser).count())
                    .author(ArticleAuthorInfoDTO.builder()
                            .username(article.getAuthor().getUsername())
                            .bio(article.getAuthor().getBio())
                            .image(article.getAuthor().getImage())
                            .following(me != null && me.getFollows() != null && me.getFollows().stream().map(Follow::getToUser).collect(Collectors.toList()).contains(article.getAuthor()))
                            .build())
                    .build());
        }
        return ArticleFindAllResponseDTO.builder()
                .articles(response)
                .articlesCount((long) response.size())
                .build();
    }

    private ArticleSingleResponseDTO getArticleSingleResponseDTO(User me, Article article, List<String> tagNames) {
        return ArticleSingleResponseDTO.builder()
                .slug(article.getSlug())
                .title(article.getTitle())
                .description(article.getDescription())
                .body(article.getBody())
                .tagList(tagNames)
                .createdAt(article.getCreateAt())
                .updatedAt(article.getUpdateAt())
                .favorited(me != null && article.getFavorites().stream().map(Favorite::getUser).collect(Collectors.toList()).contains(me))
                .favoritesCount(article.getFavorites().stream().map(Favorite::getUser).count())
                .author(ArticleAuthorInfoDTO.builder()
                        .username(article.getAuthor().getUsername())
                        .bio(article.getAuthor().getBio())
                        .image(article.getAuthor().getImage())
                        .following(me != null && me.getFollows() != null && me.getFollows().stream().map(Follow::getToUser).collect(Collectors.toList()).contains(article.getAuthor()))
                        .build())
                .build();
    }

    private CommentSingleResponseDTO getCommentSingleResponseDTO(final Comment comment, final Article article, final User me) {
        return CommentSingleResponseDTO.builder()
                .id(comment.getId())
                .createAt(comment.getCreateAt())
                .updateAt(comment.getUpdateAt())
                .body(comment.getBody())
                .author(ArticleAuthorInfoDTO.builder()
                        .username(article.getAuthor().getUsername())
                        .bio(article.getAuthor().getBio())
                        .image(article.getAuthor().getImage())
                        .following(me != null && me.getFollows() != null && me.getFollows().stream().map(Follow::getToUser).collect(Collectors.toList()).contains(article.getAuthor()))
                        .build())
                .build();
    }
}
