package com.study.realworld.domain.profile.repository;

import com.study.realworld.domain.profile.entity.Follow;
import com.study.realworld.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFromUserAndToUser(final User fromUser, final User toUser);
    Optional<Follow> findByFromUserAndToUser(final User fromUser, final User toUser);
    List<Follow> findAllByFromUser(final User fromUser);
}
