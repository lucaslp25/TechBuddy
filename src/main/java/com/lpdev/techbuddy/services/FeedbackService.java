package com.lpdev.techbuddy.services;

import com.lpdev.techbuddy.dto.FeedbackCreateDTO;
import com.lpdev.techbuddy.dto.FeedbackResponseDTO;
import com.lpdev.techbuddy.exceptions.TechBuddySecurityException;
import com.lpdev.techbuddy.exceptions.TechBuddyUnprocessableException;
import com.lpdev.techbuddy.model.entities.Feedback;
import com.lpdev.techbuddy.model.entities.MentorshipSession;
import com.lpdev.techbuddy.model.entities.User;
import com.lpdev.techbuddy.model.enums.SessionStatus;
import com.lpdev.techbuddy.model.enums.UserRole;
import com.lpdev.techbuddy.repositories.FeedbackRepository;
import com.lpdev.techbuddy.utils.AuthUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final AuthUtils authUtils;
    private final MentorshipSessionService mentorshipSessionService;

    public FeedbackService(FeedbackRepository feedbackRepository, AuthUtils authUtils, MentorshipSessionService mentorshipSessionService) {
        this.feedbackRepository = feedbackRepository;
        this.authUtils = authUtils;
        this.mentorshipSessionService = mentorshipSessionService;
    }

    @Transactional
    public FeedbackResponseDTO makeFeedback(FeedbackCreateDTO dto) {

        User user = authUtils.checkAuth();

        MentorshipSession session = mentorshipSessionService.findSessionById(dto.sessionId());

        if (!user.equals(UserRole.DEV_BUDDY) && !session.getDev().equals(user)){
            throw new TechBuddySecurityException("Você não tem autorização para dar feedback para esta mentoria.");
        }

        if (session.getStatus() != SessionStatus.SCHEDULED){
            throw new TechBuddyUnprocessableException("Você não pode dar feedback em uma sessão com status: " + session.getStatus());
        }

        Feedback feedback = new Feedback();

        feedback.setDev(session.getDev());
        feedback.setMentor(session.getMentor());
        feedback.setSession(session);
        feedback.setRating(dto.rating());
        feedback.setComment(dto.comment());

        feedback = feedbackRepository.save(feedback);

        return new FeedbackResponseDTO(feedback);
    }


}
