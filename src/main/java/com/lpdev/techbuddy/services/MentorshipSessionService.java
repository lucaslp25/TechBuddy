package com.lpdev.techbuddy.services;

import com.lpdev.techbuddy.dto.MentorshipSessionResponseDTO;
import com.lpdev.techbuddy.dto.ScheduleSessionDTO;
import com.lpdev.techbuddy.exceptions.TechBuddyNotFoundException;
import com.lpdev.techbuddy.exceptions.TechBuddyUnprocessableException;
import com.lpdev.techbuddy.model.entities.MentorshipSession;
import com.lpdev.techbuddy.model.entities.User;
import com.lpdev.techbuddy.model.enums.SessionStatus;
import com.lpdev.techbuddy.repositories.MentorshipSessionRepository;
import com.lpdev.techbuddy.utils.AuthUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MentorshipSessionService {

    private final MentorshipSessionRepository mentorshipSessionRepository;
    private final AuthUtils authUtils;

    public MentorshipSessionService(MentorshipSessionRepository mentorshipSessionRepository, AuthUtils authUtils) {
        this.mentorshipSessionRepository = mentorshipSessionRepository;
        this.authUtils = authUtils;
    }

    @Transactional
    protected void saveFirstSession(MentorshipSession firstSession) {
        mentorshipSessionRepository.save(firstSession);
    }

    @Transactional
    public MentorshipSessionResponseDTO scheduleSession(ScheduleSessionDTO dto){

        MentorshipSession sesh = mentorshipSessionRepository.findById(dto.sessionId()).orElseThrow(()-> new TechBuddyNotFoundException("Nenhuma sessão encontrada com o id: " + dto.sessionId() + "."));

        User mentor = authUtils.checkAuth();

        if(!sesh.getMentorshipRequest().getMentorRequested().equals(mentor)){
            throw new TechBuddyUnprocessableException("Você não tem permissão para marcar essa mentoria!");
        }

        if (sesh.getStatus() != SessionStatus.WAITING_FOR_SCHEDULE) {
            throw new TechBuddyUnprocessableException("Essa sesh já não está esperando agendamento. Status atual: " + sesh.getStatus());
        }

        sesh.setScheduledAt(dto.scheduledAt());
        sesh.setDuration(Duration.ofMinutes(dto.durationInMinutes()));
        sesh.setStatus(SessionStatus.SCHEDULED);
        sesh.setPlatformUrl(dto.platformUrl());
        if (dto.topic().isEmpty() || dto.topic().isBlank()) {
            sesh.setTopic("Mentoria sem tópico específico!");
        }else {
            sesh.setTopic(dto.topic());
        }

        return new MentorshipSessionResponseDTO(sesh);
    }

    @Transactional
    public void mentorshipSessionCancelled(Long sessionId) {

        MentorshipSession sesh = mentorshipSessionRepository.findById(sessionId).orElseThrow(()-> new TechBuddyNotFoundException("Nenhuma sessão de mentoria com o id: " + sessionId + " foi encontrada!"));

        User user = authUtils.checkAuth();

        if (!sesh.getMentor().equals(user) && !sesh.getDev().equals(user)) {
            throw new TechBuddyUnprocessableException("Você não tem permissão para cancelar essa mentoria!");
        }

        if (sesh.getStatus() != SessionStatus.SCHEDULED && sesh.getStatus() != SessionStatus.WAITING_FOR_SCHEDULE) {
            throw new TechBuddyUnprocessableException("Essa sessão não pode mais ser cancelada, pois seu status atual é: " + sesh.getStatus());
        }


        sesh.setStatus(SessionStatus.CANCELLED);
    }

    @Transactional(readOnly = true)
    public Set<MentorshipSessionResponseDTO> findMySessions(){

        User user = authUtils.checkAuth();

        Set<MentorshipSession> sessions = mentorshipSessionRepository.findAllSessionsByUserId(user.getId());

        return sessions.stream().map(MentorshipSessionResponseDTO::new).collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    protected MentorshipSession findSessionById(Long sessionId) {

        MentorshipSession sesh = mentorshipSessionRepository.findById(sessionId).orElseThrow(()-> new TechBuddyNotFoundException("Nenhuma sessão com o id: " + sessionId + " foi encontrada!"));

        return sesh;
    }


    @Transactional
    public void mentorshipSessionCompletedStatus(Long sessionId) {

        MentorshipSession sesh = mentorshipSessionRepository.findById(sessionId).orElseThrow(()-> new TechBuddyNotFoundException("Nenhuma sessão de mentoria com o id: " + sessionId + " foi encontrada!"));

        User user = authUtils.checkAuth();

        if (!sesh.getMentor().equals(user) && !sesh.getDev().equals(user)) {
            throw new TechBuddyUnprocessableException("Você não tem permissão para marcar essa mentoria como concluida!");
        }

        if (sesh.getStatus() != SessionStatus.SCHEDULED) {
            throw new TechBuddyUnprocessableException("Essa sessão não pode ser concluida, pois seu status atual é: " + sesh.getStatus());
        }

        sesh.setStatus(SessionStatus.COMPLETED);
    }

}
