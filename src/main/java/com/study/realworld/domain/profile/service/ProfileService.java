package com.study.realworld.domain.profile.service;

import com.study.realworld.domain.profile.dto.ProfileInfoResponseDTO;
import com.study.realworld.domain.profile.entity.Follow;
import com.study.realworld.domain.profile.repository.FollowRepository;
import com.study.realworld.domain.user.entity.User;
import com.study.realworld.domain.user.repository.UserRepository;
import com.study.realworld.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Autowired
    public ProfileService(final UserRepository userRepository, final FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
    }

    @Transactional(readOnly = true)
    public ProfileInfoResponseDTO getMyInfoSecurityAndUserInfoByUsername(final String username) {
        User from = userRepository.findById(SecurityUtils.getCurrentMemberId()).orElseThrow(() -> new IllegalArgumentException("로그인 회원 정보가 없습니다"));
        User to = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(username + " 회원 정보가 없습니다"));
        boolean isExists = followRepository.existsByFromUserAndToUser(from, to);
        return ProfileInfoResponseDTO.builder()
                .username(to.getUsername())
                .bio(to.getBio())
                .image(to.getImage())
                .following(isExists)
                .build();
    }

    @Transactional
    public ProfileInfoResponseDTO followUser(final String username) {
        User from = userRepository.findById(SecurityUtils.getCurrentMemberId()).orElseThrow(() -> new IllegalArgumentException("로그인 회원 정보가 없습니다"));
        User to = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(username + " 회원 정보가 없습니다"));
        boolean isExists = followRepository.existsByFromUserAndToUser(from, to);
        if (!isExists && !from.getId().equals(to.getId())) {
            Follow follow = new Follow(from, to);
            followRepository.save(follow);
        }
        return ProfileInfoResponseDTO.builder()
                .username(to.getUsername())
                .bio(to.getBio())
                .image(to.getImage())
                .following(isExists)
                .build();
    }

    @Transactional
    public ProfileInfoResponseDTO unFollowUser(final String username) {
        User from = userRepository.findById(SecurityUtils.getCurrentMemberId()).orElseThrow(() -> new IllegalArgumentException("로그인 회원 정보가 없습니다"));
        User to = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException(username + " 회원 정보가 없습니다"));
        followRepository.findByFromUserAndToUser(from, to).ifPresent(followRepository::delete);
        return ProfileInfoResponseDTO.builder()
                .username(to.getUsername())
                .bio(to.getBio())
                .image(to.getImage())
                .following(false)
                .build();
    }
}
