package com.study.realworld.controller;

import com.study.realworld.dto.UserDTO;
import com.study.realworld.dto.UserRegisterDTO;
import com.study.realworld.dto.UserUpdateDTO;
import com.study.realworld.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@Slf4j
public class UserController {

    private final UserService userService;
    private static final String responseOkPrefix = "user";
    private static final String responseErrorPrefix = "error";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("users")
    public ResponseEntity register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        UserDTO userDTO;
        try {
            userDTO = userService.save(userRegisterDTO);
            return ResponseEntity.status(201)
                    .body(getUserOkResponse(userDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(getUserBadResponse(e.getMessage()));
        }
    }

    @PutMapping("users")
    public ResponseEntity update(@Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        UserDTO userDTO;
        try {
            userDTO = userService.update(userUpdateDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(getUserBadResponse(e.getMessage()));
        }
        return ResponseEntity.ok()
                .body(getUserOkResponse(userDTO));
    }

    private HashMap<String, Object> getUserOkResponse(final UserDTO userDTO) {
        return new HashMap<>() {
            {
                put(UserController.responseOkPrefix, userDTO);
            }
        };
    }

    private HashMap<String, Object> getUserBadResponse(final String message) {
        return new HashMap<>() {
            {
                put(UserController.responseErrorPrefix, message);
            }
        };
    }
}
