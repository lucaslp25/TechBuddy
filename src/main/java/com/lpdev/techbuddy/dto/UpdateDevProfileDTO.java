package com.lpdev.techbuddy.dto;

import com.lpdev.techbuddy.model.entities.DevProfile;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDevProfileDTO extends UserProfileDTO{


        private String learningGoals;

        @NotBlank(message = "Você precisa informar seu nivel atual para a plataforma poder lhe ajudar melhor.")
        private String currentSkillsLevel;

    public UpdateDevProfileDTO(DevProfile devProfile){
        super();

        this.setProfileName(devProfile.getProfileName());
        this.setHeadline(devProfile.getHeadline());
        this.setProfileBio(devProfile.getProfileBio());
        this.setProfilePictureUrl(devProfile.getProfilePictureUrl());
        this.setProfileLocation(devProfile.getProfileLocation());
        this.setProfileLinkedinUrl(devProfile.getProfileLinkedinUrl());
        this.setProfileGithubUrl(devProfile.getProfileGithubUrl());
        this.setProfileStacks(devProfile.getProfileStacks());

        this.learningGoals = devProfile.getLearningGoals();
        this.currentSkillsLevel = devProfile.getCurrentSkillsLevel();
    }

}
