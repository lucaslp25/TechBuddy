package com.lpdev.techbuddy.services;

import com.lpdev.techbuddy.dto.UpdateDevProfileDTO;
import com.lpdev.techbuddy.dto.UpdateMentorProfileDTO;
import com.lpdev.techbuddy.dto.UserProfileDTO;
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
            throw new TechBuddyUnprocessableException("Não foi possivel atualizar o perfil: Seu perfil não é de desenvolvedor.");
        }
        userRepository.save(profile.getUser());
    }

    @Transactional
    public void updateMyMentorProfile(UpdateMentorProfileDTO dtoRef) {

        UserProfile profile = getUserProfile();

        if (profile instanceof MentorProfile mentorProfile) {
            updateMentorProfileFields(mentorProfile, dtoRef);
        }else{
            throw new TechBuddyUnprocessableException("Não foi possivel atualizar o perfil: Seu perfil não é de Mentor.");
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
        updateProfile(devProfile, dto);

        devProfile.setLearningGoals(dto.getLearningGoals());
        devProfile.setCurrentSkillsLevel(dto.getCurrentSkillsLevel());
    }

    private void updateMentorProfileFields(MentorProfile mentorProfile, UpdateMentorProfileDTO dto) {
        updateProfile(mentorProfile, dto);

        mentorProfile.setExperienceYears(dto.getExperienceYears());
        mentorProfile.setCompany(dto.getCompany());
        mentorProfile.setProfessionalTitle(dto.getProfessionalTitle());
        mentorProfile.setAvaliableForMentoring(dto.isAvaliableForMentoring());
    }

    private void updateProfile(UserProfile userProfile, UserProfileDTO dto){

        userProfile.setProfileName(dto.getProfileName());
        userProfile.setHeadline(dto.getHeadline());
        userProfile.setProfileBio(dto.getProfileBio());
        userProfile.setProfilePictureUrl(dto.getProfilePictureUrl());
        userProfile.setProfileLocation(dto.getProfileLocation());
        userProfile.setProfileLinkedinUrl(dto.getProfileLinkedinUrl());
        userProfile.setProfileGithubUrl(dto.getProfileGithubUrl());
        userProfile.setProfileStacks(dto.getProfileStacks());
    }
}
