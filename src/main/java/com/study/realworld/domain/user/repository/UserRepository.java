package com.study.realworld.domain.user.repository;

import com.study.realworld.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(final String email);

    boolean existsByEmail(final String email);

    Optional<User> findByUsername(final String username);
}
