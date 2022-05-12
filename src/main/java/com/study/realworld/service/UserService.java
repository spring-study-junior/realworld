package com.study.realworld.service;

import com.study.realworld.dto.UserDTO;
import com.study.realworld.entity.User;
import com.study.realworld.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDTO save(UserDTO userDTO) {
        return new UserDTO(userRepository.save(new User(userDTO)));
    }
}
