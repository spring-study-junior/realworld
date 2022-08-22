package com.study.realworld.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserResponseDTO {
    private UserDTO user;

    public UserResponseDTO(UserDTO userDTO) {
        this.user = UserDTO.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .bio(userDTO.getBio())
                .image(userDTO.getImage())
                .build();
    }
}
