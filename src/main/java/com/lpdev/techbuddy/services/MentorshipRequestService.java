package com.lpdev.techbuddy.services;

import com.lpdev.techbuddy.dto.MentorshipRequestCreateDTO;
import com.lpdev.techbuddy.dto.MentorshipRequestResponseDTO;
import com.lpdev.techbuddy.exceptions.TechBuddyNotFoundException;
import com.lpdev.techbuddy.exceptions.TechBuddySecurityException;
import com.lpdev.techbuddy.exceptions.TechBuddyUnprocessableException;
import com.lpdev.techbuddy.model.entities.MentorProfile;
import com.lpdev.techbuddy.model.entities.MentorshipRequest;
import com.lpdev.techbuddy.model.entities.MentorshipSession;
import com.lpdev.techbuddy.model.entities.User;
import com.lpdev.techbuddy.model.enums.RequestStatus;
import com.lpdev.techbuddy.model.enums.SessionStatus;
import com.lpdev.techbuddy.model.enums.UserRole;
import com.lpdev.techbuddy.repositories.MentorshipRequestRepository;
import com.lpdev.techbuddy.repositories.UserRepository;
import com.lpdev.techbuddy.utils.AuthUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MentorshipRequestService {

    private final MentorshipRequestRepository mentorshipRequestRepository;
    private final UserRepository userRepository;
    private final MentorshipSessionService mentorshipSessionService;
    private final AuthUtils authUtils;

    public MentorshipRequestService(MentorshipRequestRepository mentorshipRequestRepository, UserRepository userRepository, MentorshipSessionService mentorshipSessionService, AuthUtils authUtils) {
        this.mentorshipRequestRepository = mentorshipRequestRepository;
        this.userRepository = userRepository;
        this.mentorshipSessionService = mentorshipSessionService;
        this.authUtils = authUtils;
    }

    @Transactional
    public MentorshipRequestResponseDTO createRequestForMentor(MentorshipRequestCreateDTO dto){

        User requesterUser = authUtils.checkAuth();

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

        final String DEFAULT_MESSAGE = "Olá, " + mentorUser.getUserProfile().getProfileName() + "! Eu me chamo " + requesterUser.getName() + " e gostei muito do seu perfil e vi que as stacks são exatamente as que eu estava procurando! Gostaria muito de ter uma mentoria com você para melhorar meu lado técnico e trocar conhecimentos com você. Aguardo sua resposta!" ;

        if (dto.message() == null || dto.message().isEmpty() || dto.message().isBlank()){
            mentorRequest.setMessage(DEFAULT_MESSAGE);
        }else{
            mentorRequest.setMessage(dto.message());
        }

        mentorRequest = mentorshipRequestRepository.save(mentorRequest);

        return new MentorshipRequestResponseDTO(mentorRequest);
    }

    @Transactional
    public void acceptRequest(Long id){

        MentorshipRequest request = mentorshipRequestRepository.findById(id).orElseThrow(()-> new TechBuddyNotFoundException("Nenhuma request com o id: " + id + " foi encontrada."));

        User user = authUtils.checkAuth();

        if (!request.getMentorRequested().equals(user) || user.getRole() != UserRole.MENTOR_BUDDY){
            throw new TechBuddySecurityException("Você não tem permissão para modificar essa solicitação.");
        }

        request.setRequestStatus(RequestStatus.ACCEPTED); //emitir notificação
        request.setUpdatedAt(Instant.now());

        MentorshipSession session = new MentorshipSession();

        session.setDev(request.getDevRequester());
        session.setMentor(request.getMentorRequested());
        session.setMentorshipRequest(request); ///id se nao der
        session.setStatus(SessionStatus.WAITING_FOR_SCHEDULE);

        mentorshipSessionService.saveFirstSession(session);
    }

    @Transactional
    public void recuseRequest(Long id){

        User currentUser = authUtils.checkAuth();

        MentorshipRequest request = mentorshipRequestRepository.findById(id)
                .orElseThrow(()-> new TechBuddyNotFoundException("Nenhuma request com o id: " + id + " foi encontrada."));

        if (!request.getMentorRequested().equals(currentUser)) {
            throw new TechBuddySecurityException("Você não tem permissão para recusar esta solicitação.");
        }

        if (request.getRequestStatus() != RequestStatus.PENDING) {
            throw new TechBuddyUnprocessableException("Esta solicitação já foi processada.");
        }

        request.setRequestStatus(RequestStatus.RECUSED);
        request.setUpdatedAt(Instant.now());

        mentorshipRequestRepository.save(request);
    }

    @Transactional(readOnly = true)
    public Set<MentorshipRequestResponseDTO> findMyRequests(){

        User user = authUtils.checkAuth();

        Set<MentorshipRequest> entities = mentorshipRequestRepository.findAllRequestsByUserId(user.getId());

        return entities.stream().map(MentorshipRequestResponseDTO::new).collect(Collectors.toSet());
    }

}
