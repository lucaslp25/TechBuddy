package com.lpdev.techbuddy.dto;

import com.lpdev.techbuddy.model.entities.MentorshipRequest;
import com.lpdev.techbuddy.model.enums.RequestStatus;

import java.time.Instant;

public record MentorshipRequestResponseDTO
        (
                Long id,
                String devName,
                String mentorName,
                String message,
                RequestStatus requestStatus,
                Instant createdAt
        )
{
    public MentorshipRequestResponseDTO(MentorshipRequest entity){
        this(entity.getId(), entity.getDevRequester().getName(), entity.getMentorRequested().getName(), entity.getMessage(), entity.getRequestStatus(), entity.getCreatedAt());
    }
}
