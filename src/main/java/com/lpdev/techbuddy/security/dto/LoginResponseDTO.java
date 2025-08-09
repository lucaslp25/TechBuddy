package com.lpdev.techbuddy.security.dto;

import com.lpdev.techbuddy.model.entities.User;

public record LoginResponseDTO(

        String token,

        String name,

        String email

) {
    public LoginResponseDTO(String token, User entity){
        this(token, entity.getName(), entity.getEmail());
    }
}
