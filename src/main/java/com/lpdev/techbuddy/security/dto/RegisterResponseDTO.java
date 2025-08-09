package com.lpdev.techbuddy.security.dto;

import com.lpdev.techbuddy.model.entities.User;
import com.lpdev.techbuddy.model.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterResponseDTO(

        @NotBlank(message = "O nome não pode ficar vazio.")
        String name,

        @NotBlank(message = "O email não pode ficar vazio.")
        String email,

        @NotNull(message = "O role não pode ficar vazio.")
        UserRole role
) {

    public RegisterResponseDTO(User entity){
        this(entity.getName(), entity.getEmail(), entity.getRole());
    }
}
