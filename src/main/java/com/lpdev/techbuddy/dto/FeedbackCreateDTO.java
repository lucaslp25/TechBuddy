package com.lpdev.techbuddy.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;

public record FeedbackCreateDTO(

        @NotNull(message = "Você deve dar uma nota para essa mentoria e mentor.")
        Double rating,

        @Lob
        String comment,

        @Column(unique = true, nullable = false)
        Long sessionId
) {

}
