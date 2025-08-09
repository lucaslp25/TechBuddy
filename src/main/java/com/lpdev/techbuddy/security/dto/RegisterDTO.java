package com.lpdev.techbuddy.security.dto;

import com.lpdev.techbuddy.model.entities.User;
import com.lpdev.techbuddy.model.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(

        @NotBlank(message = "O nome não pode ficar vazio.")
        String name,

        @NotBlank(message = "O email não pode ficar vazio.")
        String email,

        @NotBlank(message = "A senha não pode ficar vazia.")
        String password,

        @NotNull(message = "O role não pode ficar vazio.")
        UserRole role
) {

    public RegisterDTO(User entity){
        this(entity.getName(), entity.getEmail(), entity.getPassword(), entity.getRole());
    }
}
