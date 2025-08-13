package com.lpdev.techbuddy.services;

import com.lpdev.techbuddy.dto.MentorProfileViewDTO;
import com.lpdev.techbuddy.dto.UpdateDevProfileDTO;
import com.lpdev.techbuddy.dto.UpdateMentorProfileDTO;
import com.lpdev.techbuddy.dto.UserProfileDTO;
import com.lpdev.techbuddy.exceptions.TechBuddyNotFoundException;
import com.lpdev.techbuddy.exceptions.TechBuddyUnprocessableException;
import com.lpdev.techbuddy.model.entities.DevProfile;
import com.lpdev.techbuddy.model.entities.MentorProfile;
import com.lpdev.techbuddy.model.entities.User;
import com.lpdev.techbuddy.model.entities.UserProfile;
import com.lpdev.techbuddy.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

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
    }

    @Transactional
    public void updateMyMentorProfile(UpdateMentorProfileDTO dtoRef) {

        UserProfile profile = getUserProfile();

        if (profile instanceof MentorProfile mentorProfile) {
            updateMentorProfileFields(mentorProfile, dtoRef);
        }else{
            throw new TechBuddyUnprocessableException("Não foi possivel atualizar o perfil: Seu perfil não é de Mentor.");
        }
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
        mentorProfile.setAvailableForMentoring(dto.isAvailableForMentoring());
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

    @Transactional(readOnly = true)
    public Set<MentorProfileViewDTO> findAllMentorProfiles() {

        Set<MentorProfile> mentors = userRepository.findAllMentorProfiles();

        return mentors.stream().map(MentorProfileViewDTO::new).collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Set<MentorProfileViewDTO> findAllAvailableMentorProfiles() {

        Set<MentorProfile> mentors = userRepository.findAllAvailableMentorProfiles();

        return mentors.stream().map(MentorProfileViewDTO::new).collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public MentorProfileViewDTO findMentorProfileByUsername(String username) {

        MentorProfile mentor = userRepository.findMentorProfileByUsername(username).orElseThrow(()-> new TechBuddyNotFoundException("Nenhum mentor com o username: " + username + " foi encontrado."));

        return new MentorProfileViewDTO(mentor);
    }

    @Transactional(readOnly = true)
    public Set<MentorProfileViewDTO> findMentorsProfileByStack(String stack) {

        Set<MentorProfile> mentors = userRepository.findAllMentorProfilesByStack(stack);

        return mentors.stream().map(MentorProfileViewDTO::new).collect(Collectors.toSet());
    }
}
