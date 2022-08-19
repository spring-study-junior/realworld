package com.study.realworld.domain.user.controller;

import com.study.realworld.domain.user.dto.UserLoginRequestDTO;
import com.study.realworld.domain.user.dto.UserLoginResponseDTO;
import com.study.realworld.domain.user.dto.UserRegisterRequestDTO;
import com.study.realworld.domain.user.dto.UserRegisterResponseDTO;
import com.study.realworld.domain.user.dto.UserUpdateRequestDTO;
import com.study.realworld.domain.user.dto.UserUpdateResponseDTO;
import com.study.realworld.domain.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Validated
@Slf4j
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserRegisterResponseDTO register(@Valid @RequestBody UserRegisterRequestDTO requestDTO) {
        return userService.save(requestDTO);
    }

    @PutMapping
    public UserUpdateResponseDTO update(@Valid @RequestBody UserUpdateRequestDTO requestDTO) {
        return userService.update(requestDTO);
    }

    @PostMapping("/login")
    public UserLoginResponseDTO login(@Valid @RequestBody UserLoginRequestDTO requestDTO) {
        return userService.login(requestDTO);
    }
}
