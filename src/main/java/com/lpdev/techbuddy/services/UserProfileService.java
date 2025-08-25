package com.lpdev.techbuddy.services;

import com.lpdev.techbuddy.dto.*;
import com.lpdev.techbuddy.exceptions.TechBuddyNotFoundException;
import com.lpdev.techbuddy.exceptions.TechBuddyUnprocessableException;
import com.lpdev.techbuddy.model.entities.DevProfile;
import com.lpdev.techbuddy.model.entities.MentorProfile;
import com.lpdev.techbuddy.model.entities.User;
import com.lpdev.techbuddy.model.entities.UserProfile;
import com.lpdev.techbuddy.repositories.MentorProfileRepository;
import com.lpdev.techbuddy.repositories.MentorSpecifications;
import com.lpdev.techbuddy.repositories.UserRepository;
import com.lpdev.techbuddy.utils.AuthUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserProfileService {

    private final UserRepository userRepository;
    private final AuthUtils authUtils;
    private final MentorProfileRepository mentorProfileRepository;

    public UserProfileService(UserRepository userRepository, AuthUtils authUtils, MentorProfileRepository mentorProfileRepository) {
        this.userRepository = userRepository;
        this.authUtils = authUtils;
        this.mentorProfileRepository = mentorProfileRepository;
    }

    @Transactional
    public void updateMyDevProfile(UpdateDevProfileDTO dtoRef) {

        User user = authUtils.checkAuth();

        UserProfile profile = user.getUserProfile();

        if (profile instanceof DevProfile devProfile) {
            updateDevProfileFields(devProfile, dtoRef);
        }else{
            throw new TechBuddyUnprocessableException("Não foi possivel atualizar o perfil: Seu perfil não é de desenvolvedor.");
        }
    }

    @Transactional
    public void updateMyMentorProfile(UpdateMentorProfileDTO dtoRef) {

        User user = authUtils.checkAuth();

        UserProfile profile = user.getUserProfile();

        if (profile instanceof MentorProfile mentorProfile) {
            updateMentorProfileFields(mentorProfile, dtoRef);
        }else{
            throw new TechBuddyUnprocessableException("Não foi possivel atualizar o perfil: Seu perfil não é de Mentor.");
        }
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

        List<MentorProfile> mentors = mentorProfileRepository.findAll();

        return mentors.stream().map(MentorProfileViewDTO::new).collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public Set<MentorProfileViewDTO> findAllAvailableMentorProfiles() {

        Set<MentorProfile> mentors = mentorProfileRepository.findAllAvailableMentorProfiles();

        return mentors.stream().map(MentorProfileViewDTO::new).collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public MentorProfileViewDTO findMentorProfileByUsername(String username) {

        MentorProfile mentor = mentorProfileRepository.findMentorProfileByUsername(username).orElseThrow(()-> new TechBuddyNotFoundException("Nenhum mentor com o username: " + username + " foi encontrado."));

        return new MentorProfileViewDTO(mentor);
    }

    @Transactional(readOnly = true)
    public Set<MentorProfileViewDTO> findMentorsProfileByStack(String stack) {

        Set<MentorProfile> mentors = mentorProfileRepository.findAllMentorProfilesByStack(stack);

        return mentors.stream().map(MentorProfileViewDTO::new).collect(Collectors.toSet());
    }

    @Transactional
    public Set<MentorProfileViewDTO> findMentorByCriteria(MentorSearchCriteriaDTO dto){

        Specification<MentorProfile> spec = MentorSpecifications.withCriteria(dto);

        List<MentorProfile> mentors = mentorProfileRepository.findAll(spec);

        return mentors.stream()
                .map(MentorProfileViewDTO::new)
                .collect(Collectors.toSet());

    }
}
