package com.study.realworld.domain.user.service;

import com.study.realworld.domain.user.repository.UserRepository;
import com.study.realworld.domain.user.dto.UserRegisterRequestDTO;
import com.study.realworld.domain.user.dto.UserRegisterResponseDTO;
import com.study.realworld.domain.user.dto.UserUpdateRequestDTO;
import com.study.realworld.domain.user.dto.UserUpdateResponseDTO;
import com.study.realworld.domain.user.entity.User;
import com.study.realworld.configuration.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserRegisterResponseDTO save(UserRegisterRequestDTO requestDTO) {
        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 Email 입니다.");
        }
        User user = userRepository.save(requestDTO.toEntityWithPasswordEncode(passwordEncoder));
        return UserRegisterResponseDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    @Transactional
    public UserUpdateResponseDTO update(UserUpdateRequestDTO requestDTO) {
        User user = userRepository.findByEmail(requestDTO.getEmail()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Email 입니다."));
        if (StringUtils.isNotNullAndNotEmpty(requestDTO.getUsername())) {
            user.setUsername(requestDTO.getUsername());
        }
        if (StringUtils.isNotNullAndNotEmpty(requestDTO.getPassword())) {
            user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        }
        if (StringUtils.isNotNullAndNotEmpty(requestDTO.getBio())) {
            user.setBio(requestDTO.getBio());
        }
        if (StringUtils.isNotNullAndNotEmpty(requestDTO.getImage())) {
            user.setImage(requestDTO.getImage());
        }
        return UserUpdateResponseDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .bio(user.getBio())
                .image(user.getImage())
                .build();
    }
}
