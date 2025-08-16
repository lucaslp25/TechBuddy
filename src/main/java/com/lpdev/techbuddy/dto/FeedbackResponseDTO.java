package com.lpdev.techbuddy.dto;

import com.lpdev.techbuddy.model.entities.Feedback;

import java.time.Instant;

public record FeedbackResponseDTO(Long id, Double averageRating, String comment, Long sessionId, String devName, String mentorName, Instant createdAt) {

    public FeedbackResponseDTO(Feedback entity){
        this(entity.getId(), entity.getRating(), entity.getComment(), entity.getSession().getId(), entity.getDev().getName(), entity.getMentor().getName(), entity.getCreatedAt());
    }

}
