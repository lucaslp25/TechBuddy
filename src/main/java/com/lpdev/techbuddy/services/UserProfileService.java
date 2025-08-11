package com.lpdev.techbuddy.services;

import com.lpdev.techbuddy.dto.UpdateDevProfileDTO;
import com.lpdev.techbuddy.dto.UpdateMentorProfileDTO;
import com.lpdev.techbuddy.exceptions.TechBuddyUnprocessableException;
import com.lpdev.techbuddy.model.entities.DevProfile;
import com.lpdev.techbuddy.model.entities.MentorProfile;
import com.lpdev.techbuddy.model.entities.User;
import com.lpdev.techbuddy.model.entities.UserProfile;
import com.lpdev.techbuddy.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileService {

    private final UserRepository userRepository;

    public UserProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void updateMyDevProfile(UpdateDevProfileDTO dtoRef) {

        UserProfile profile = getUserProfile();

        if (profile instanceof DevProfile devProfile) {
            updateDevProfileFields(devProfile, dtoRef);
        }else{
            throw new TechBuddyUnprocessableException("Tipo de perfil e/ou dados enviados inválidos.");
        }
        userRepository.save(profile.getUser());
    }

    @Transactional
    public void updateMyMentorProfile(UpdateMentorProfileDTO dtoRef) {

        UserProfile profile = getUserProfile();

        if (profile instanceof MentorProfile mentorProfile) {
            updateMentorProfileFields(mentorProfile, dtoRef);
        }else{
            throw new TechBuddyUnprocessableException("Tipo de perfil e/ou dados enviados inválidos.");
        }
        userRepository.save(profile.getUser());
    }


    private UserProfile getUserProfile() {

        //pegando o contexto do usuario logado
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findUserByEmail(userEmail).orElseThrow(() -> new IllegalStateException("User not found"));

        UserProfile profile = user.getUserProfile();

        return profile;
    }


    private void updateDevProfileFields(DevProfile devProfile, UpdateDevProfileDTO dto) {

        devProfile.setProfileName(dto.profileName());
        devProfile.setHeadline(dto.headline());
        devProfile.setProfileBio(dto.profileBio());
        devProfile.setProfilePictureUrl(dto.profilePictureUrl());
        devProfile.setProfileLocation(dto.profileLocation());
        devProfile.setProfileLinkedinUrl(dto.profileLinkedinUrl());
        devProfile.setProfileGithubUrl(dto.profileGithubUrl());
        devProfile.setLearningGoals(dto.learningGoals());
        devProfile.setCurrentSkillsLevel(dto.currentSkillsLevel());
        devProfile.setProfileStacks(dto.stacks());
    }

    private void updateMentorProfileFields(MentorProfile mentorProfile, UpdateMentorProfileDTO dto) {

        mentorProfile.setProfileName(dto.profileName());
        mentorProfile.setHeadline(dto.headline());
        mentorProfile.setProfileBio(dto.profileBio());
        mentorProfile.setProfilePictureUrl(dto.profilePictureUrl());
        mentorProfile.setProfileLocation(dto.profileLocation());
        mentorProfile.setProfileLinkedinUrl(dto.profileLinkedinUrl());
        mentorProfile.setProfileGithubUrl(dto.profileGithubUrl());
        mentorProfile.setProfileStacks(dto.stacks());
        mentorProfile.setExperienceYears(dto.experienceYears());
        mentorProfile.setCompany(dto.company());
        mentorProfile.setProfissionalTitle(dto.profissionalTitle());
        mentorProfile.setAvaliableForMentoring(dto.avaliableForMentoring());
    }
}
