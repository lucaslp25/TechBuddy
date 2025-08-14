package com.lpdev.techbuddy.dto;

import com.lpdev.techbuddy.model.entities.MentorshipRequest;
import jakarta.validation.constraints.NotNull;

public record MentorshipRequestCreateDTO(

        @NotNull(message = "O campo 'mentorId' é obrigatório.")
        Long mentorId,

        String message
) {

    public MentorshipRequestCreateDTO(MentorshipRequest entity) {
        this(entity.getId(), entity.getMessage());
    }

}
