package com.lpdev.techbuddy.dto;

import com.lpdev.techbuddy.model.entities.MentorProfile;

import java.util.Set;

public record UpdateMentorProfileDTO(
        String profileName,
        String headline,
        String profileBio,
        String profilePictureUrl,
        String profileLocation,
        String profileLinkedinUrl,
        String profileGithubUrl,
        Set<String> stacks,
        Integer experienceYears,
        String company,
        String profissionalTitle,
        boolean avaliableForMentoring

) {

    public UpdateMentorProfileDTO(MentorProfile entity){
        this(entity.getProfileName(), entity.getHeadline(), entity.getProfileBio(), entity.getProfilePictureUrl(), entity.getProfileLocation(), entity.getProfileLinkedinUrl(), entity.getProfileGithubUrl(), entity.getProfileStacks(), entity.getExperienceYears(), entity.getCompany(), entity.getProfissionalTitle(), entity.isAvaliableForMentoring());
    }

}
