package com.lpdev.techbuddy.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.util.Set;

@Getter
@Setter
public abstract class UserProfileDTO {

        @NotBlank(message = "O usuário precisa ter um username.")
        private String profileName;
        @NotBlank(message = "O usuário precisa ter um headline.")
        private String headline;

        private String profileBio;

        private String profilePictureUrl;

        private String profileLocation;

        @Nullable
        @URL(message = "O endereço do linkedin deve ser uma URL válida.")
        private String profileLinkedinUrl;

        @Nullable
        @URL(protocol = "https", host = "github.com", message = "O endereço do Github deve ser uma URL válida.")
        private String profileGithubUrl;

        private Set<String> profileStacks;
}