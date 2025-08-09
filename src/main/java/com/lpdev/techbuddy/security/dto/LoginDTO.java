package com.lpdev.techbuddy.security.dto;

import com.lpdev.techbuddy.model.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginDTO(

        @Email
        @NotBlank(message = "O email não pode ficar vazio.")
        String email,

        @NotBlank(message = "A senha não pode ficar vazia.")
        String password
) {

    public LoginDTO(User entity){
        this(entity.getEmail(), entity.getPassword());
    }
}
