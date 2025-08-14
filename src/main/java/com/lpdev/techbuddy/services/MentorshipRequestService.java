package com.lpdev.techbuddy.services;

import com.lpdev.techbuddy.dto.MentorshipRequestCreateDTO;
import com.lpdev.techbuddy.dto.MentorshipRequestResponseDTO;
import com.lpdev.techbuddy.exceptions.TechBuddyNotFoundException;
import com.lpdev.techbuddy.exceptions.TechBuddyUnprocessableException;
import com.lpdev.techbuddy.model.entities.MentorProfile;
import com.lpdev.techbuddy.model.entities.MentorshipRequest;
import com.lpdev.techbuddy.model.entities.User;
import com.lpdev.techbuddy.model.enums.RequestStatus;
import com.lpdev.techbuddy.model.enums.UserRole;
import com.lpdev.techbuddy.repositories.MentorshipRequestRepository;
import com.lpdev.techbuddy.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MentorshipRequestService {

    private final MentorshipRequestRepository mentorshipRequestRepository;
    private final UserRepository userRepository;

    public MentorshipRequestService(MentorshipRequestRepository mentorshipRequestRepository, UserRepository userRepository) {
        this.mentorshipRequestRepository = mentorshipRequestRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public MentorshipRequestResponseDTO createRequestForMentor(MentorshipRequestCreateDTO dto){

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName(); //pega o email do user logado

        User requesterUser = userRepository.findUserByEmail(userEmail).orElseThrow(()-> new TechBuddyNotFoundException("Usuário não encontrado com email: " + userEmail));

        if (requesterUser.getRole() != UserRole.DEV_BUDDY){
            throw new TechBuddyUnprocessableException("Erro, apenas users DEV podem fazer essa request.");
        }

        User mentorUser = userRepository.findById(dto.mentorId()).orElseThrow(()-> new TechBuddyNotFoundException("Nenhum mentor com o id: " + dto.mentorId() + " foi encontrado."));

        if (mentorUser.getRole() != UserRole.MENTOR_BUDDY){
            throw new TechBuddyUnprocessableException("Erro, o usuario: " + mentorUser.getName() + " não tem ROLE de MENTOR.");
        }

        if (mentorUser.getUserProfile() instanceof MentorProfile mentorProfile) {
            if (!mentorProfile.isAvailableForMentoring()) {
                throw new TechBuddyUnprocessableException("Este mentor não está disponível para novas mentorias no momento.");
            }
        } else {
            throw new TechBuddyUnprocessableException("Perfil do mentor inválido.");
        }

        MentorshipRequest mentorRequest = new MentorshipRequest();

        mentorRequest.setDevRequester(requesterUser);
        mentorRequest.setMentorRequested(mentorUser);
        mentorRequest.setRequestStatus(RequestStatus.PENDING);

        final String DEFAULT_MESSAGE = "Olá, " + mentorUser.getUserProfile().getProfileName() + "! Eu me chamo " + requesterUser.getUserProfile().getProfileName() + " e gostei muito do seu perfil e vi que as stacks são exatamente as que eu estava procurando! Gostaria muito de ter uma mentoria com você para melhorar meu lado técnico e trocar conhecimentos com você. Aguardo sua resposta!" ;

        if (dto.message() == null || dto.message().isEmpty() || dto.message().isBlank()){
            mentorRequest.setMessage(DEFAULT_MESSAGE);
        }else{
            mentorRequest.setMessage(dto.message());
        }

        mentorRequest = mentorshipRequestRepository.save(mentorRequest);

        return new MentorshipRequestResponseDTO(mentorRequest);
    }


}
