package com.lpdev.techbuddy.dto;

import com.lpdev.techbuddy.model.entities.MentorshipSession;
import com.lpdev.techbuddy.model.enums.SessionStatus;

import java.time.Instant;

public record MentorshipSessionResponseDTO(

        Long id,
        String dev,
        String mentor,
        String topic,
        Instant scheduledAt,
        Integer duration_min,
        String platformUrl,
        SessionStatus status
) {

    public MentorshipSessionResponseDTO(MentorshipSession entity){
        this(
                entity.getId(),
                entity.getDev().getName(),
                entity.getMentor().getName(),
                entity.getTopic(),
                entity.getScheduledAt(),
                entity.getDuration() != null ? (int) entity.getDuration().toMinutes() : null,
                entity.getPlatformUrl(),
                entity.getStatus());
    }

}
