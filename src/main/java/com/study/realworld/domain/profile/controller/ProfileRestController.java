package com.study.realworld.domain.profile.controller;

import com.study.realworld.domain.profile.dto.ProfileInfoRequestDTO;
import com.study.realworld.domain.profile.dto.ProfileInfoResponseDTO;
import com.study.realworld.domain.profile.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/profiles")
@Validated
@Slf4j
public class ProfileRestController {

    private final ProfileService profileService;

    @Autowired
    public ProfileRestController(final ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{username}")
    public ProfileInfoResponseDTO getUserProfile(@PathVariable("username") ProfileInfoRequestDTO requestDTO) {
        return profileService.getProfile(requestDTO.getUsername());
    }

    @PostMapping("/{username}/follow")
    public ProfileInfoResponseDTO followUser(@PathVariable("username") ProfileInfoRequestDTO requestDTO) {
        return profileService.followUser(requestDTO.getUsername());
    }

    @DeleteMapping("/{username}/follow")
    public ProfileInfoResponseDTO unFollowUser(@PathVariable("username") ProfileInfoRequestDTO requestDTO) {
        return profileService.unFollowUser(requestDTO.getUsername());
    }
}
