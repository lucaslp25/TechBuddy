package com.lpdev.techbuddy.dto;

import com.lpdev.techbuddy.model.entities.MentorProfile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMentorProfileDTO extends UserProfileDTO {

    @NotNull(message = "Você como mentor precisa dizer quantos anos tem de experiência.")
    private Integer experienceYears;

    private String company;

    @NotBlank(message = "Titulo profissional obrigatório.")
    private String professionalTitle;

    private boolean avaliableForMentoring;

    public UpdateMentorProfileDTO(MentorProfile mentorProfile) {
        super();

        this.setProfileName(mentorProfile.getProfileName());
        this.setHeadline(mentorProfile.getHeadline());
        this.setProfileBio(mentorProfile.getProfileBio());
        this.setProfilePictureUrl(mentorProfile.getProfilePictureUrl());
        this.setProfileLocation(mentorProfile.getProfileLocation());
        this.setProfileLinkedinUrl(mentorProfile.getProfileLinkedinUrl());
        this.setProfileGithubUrl(mentorProfile.getProfileGithubUrl());
        this.setProfileStacks(mentorProfile.getProfileStacks());

        this.experienceYears = mentorProfile.getExperienceYears();
        this.company = mentorProfile.getCompany();
        this.professionalTitle = mentorProfile.getProfessionalTitle();
        this.avaliableForMentoring = mentorProfile.isAvaliableForMentoring();
    }

}