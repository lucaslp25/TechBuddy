package com.lpdev.techbuddy.dto;

import com.lpdev.techbuddy.model.entities.DevProfile;

import java.util.Set;


public record UpdateDevProfileDTO(
        String profileName,
        String headline,
        String profileBio,
        String profilePictureUrl,
        String profileLocation,
        String profileLinkedinUrl,
        String profileGithubUrl,
        Set<String> stacks,
        String learningGoals,
        String currentSkillsLevel
) {

    public UpdateDevProfileDTO(DevProfile entity){
        this(entity.getProfileName(), entity.getHeadline(), entity.getProfileBio(), entity.getProfilePictureUrl(), entity.getProfileLocation(),
                entity.getProfileLinkedinUrl(), entity.getProfileGithubUrl(), entity.getProfileStacks(),
                entity.getLearningGoals(), entity.getCurrentSkillsLevel());
    }

}
