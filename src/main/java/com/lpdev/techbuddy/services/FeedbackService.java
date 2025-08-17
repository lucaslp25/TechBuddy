package com.lpdev.techbuddy.services;

import com.lpdev.techbuddy.dto.FeedbackCreateDTO;
import com.lpdev.techbuddy.dto.FeedbackResponseDTO;
import com.lpdev.techbuddy.exceptions.TechBuddySecurityException;
import com.lpdev.techbuddy.exceptions.TechBuddyUnprocessableException;
import com.lpdev.techbuddy.model.entities.*;
import com.lpdev.techbuddy.model.enums.SessionStatus;
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

        if (!session.getDev().equals(user)){
            throw new TechBuddySecurityException("Você não tem autorização para dar feedback para esta mentoria.");
        }

        if (session.getStatus() != SessionStatus.COMPLETED){
            throw new TechBuddyUnprocessableException("Você não pode dar feedback em uma sessão com status: " + session.getStatus());
        }

        Feedback feedback = new Feedback();

        feedback.setDev(session.getDev());
        feedback.setMentor(session.getMentor());
        feedback.setSession(session);
        feedback.setRating(dto.rating());
        feedback.setComment(dto.comment());

        MentorProfile mentorProfile = (MentorProfile) session.getMentor().getUserProfile();

        feedback = feedbackRepository.save(feedback);

        updateMentorReputation(mentorProfile);

        return new FeedbackResponseDTO(feedback);
    }

    private void updateMentorReputation(MentorProfile mentorProfile) {

        Double avg = feedbackRepository.findAverageRatingByMentorId(mentorProfile.getId());
        Long size = feedbackRepository.countFeedbackByMentorId(mentorProfile.getId());

        mentorProfile.setAverageRating(avg != null ? avg : 0.0 );
        mentorProfile.setTotalMentorships(size);
    }

}
