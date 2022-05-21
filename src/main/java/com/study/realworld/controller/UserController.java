package com.study.realworld.controller;

import com.study.realworld.dto.UserRequestDTO;
import com.study.realworld.dto.UserResponseDTO;
import com.study.realworld.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("users")
    public UserResponseDTO register(@RequestBody UserRequestDTO user) {
        return new UserResponseDTO(userService.save(user.getUser()));
    }
}
