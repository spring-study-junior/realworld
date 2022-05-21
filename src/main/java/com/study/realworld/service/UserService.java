package com.study.realworld.service;

import com.study.realworld.dto.UserDTO;
import com.study.realworld.dto.UserRegisterDTO;
import com.study.realworld.dto.UserUpdateDTO;
import com.study.realworld.entity.User;
import com.study.realworld.repository.UserRepository;
import com.study.realworld.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

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
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public UserDTO save(UserRegisterDTO userDTO) {
        Optional<User> optionalUser = this.findByEmail(userDTO.getEmail());
        if (optionalUser.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 Email 입니다.");
        }
        return new UserDTO(userRepository.save(userDTO.toEntityWithPasswordEncode(passwordEncoder)));
    }

    @Transactional
    public UserDTO update(UserUpdateDTO userDTO) {
        Optional<User> optionalUser = this.findByEmail(userDTO.getEmail());
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 Email 입니다.");
        }
        User user = optionalUser.get();
        if (!StringUtils.isEmptyOrNull(userDTO.getUsername())) {
            user.setUsername(userDTO.getUsername());
        }
        if (!StringUtils.isEmptyOrNull(userDTO.getPassword())) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        if (!StringUtils.isEmptyOrNull(userDTO.getBio())) {
            user.setBio(userDTO.getBio());
        }
        if (!StringUtils.isEmptyOrNull(userDTO.getImage())) {
            user.setImage(userDTO.getImage());
        }
        return new UserDTO(user);
    }
}
