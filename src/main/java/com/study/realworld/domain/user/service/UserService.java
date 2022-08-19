package com.study.realworld.domain.user.service;

import com.study.realworld.domain.user.dto.UserRegisterRequestDTO;
import com.study.realworld.domain.user.dto.UserRegisterResponseDTO;
import com.study.realworld.domain.user.dto.UserUpdateRequestDTO;
import com.study.realworld.domain.user.dto.UserUpdateResponseDTO;
import com.study.realworld.domain.user.dto.UserLoginRequestDTO;
import com.study.realworld.domain.user.dto.UserLoginResponseDTO;
import com.study.realworld.domain.user.repository.UserRepository;
import com.study.realworld.domain.user.entity.User;
import com.study.realworld.security.jwt.TokenDto;
import com.study.realworld.security.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder managerBuilder;
    private final TokenProvider tokenProvider;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManagerBuilder managerBuilder, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.managerBuilder = managerBuilder;
        this.tokenProvider = tokenProvider;
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
        if (StringUtils.hasText(requestDTO.getUsername())) {
            user.setUsername(requestDTO.getUsername());
        }
        if (StringUtils.hasText(requestDTO.getPassword())) {
            user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        }
        if (StringUtils.hasText(requestDTO.getBio())) {
            user.setBio(requestDTO.getBio());
        }
        if (StringUtils.hasText(requestDTO.getImage())) {
            user.setImage(requestDTO.getImage());
        }
        return UserUpdateResponseDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .bio(user.getBio())
                .image(user.getImage())
                .build();
    }

    @Transactional
    public UserLoginResponseDTO login(UserLoginRequestDTO requestDTO) {
        if (!userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new IllegalArgumentException("존재하지 않는 Email 입니다.");
        }
        UsernamePasswordAuthenticationToken authenticationToken = requestDTO.toAuthentication();
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        return UserLoginResponseDTO.builder()
                .email(requestDTO.getEmail())
                .token(tokenDto.getAccessToken())
                .build();
    }
}
