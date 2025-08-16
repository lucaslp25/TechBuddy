package com.lpdev.techbuddy.dto;

import com.lpdev.techbuddy.model.entities.MentorshipSession;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.URL;

import java.time.Instant;

public record ScheduleSessionDTO(

        Long sessionId,

        @NotNull(message = "A data e hora do agendamento são obrigatórias.")
        @Future(message = "A data do agendamento deve ser no futuro.")
        Instant scheduledAt,

        @NotNull(message = "A duração é obrigatória.")
        @Positive(message = "A duração deve ser um número positivo.")
        Integer durationInMinutes,

        @NotBlank(message = "A URL da plataforma é obrigatória.")
        @URL(message = "A URL da plataforma deve ser válida.")
        String platformUrl,

        String topic
) {
    public ScheduleSessionDTO(MentorshipSession entity){
        this(entity.getId(), entity.getScheduledAt(), (int) entity.getDuration().toMinutes(), entity.getPlatformUrl(), entity.getTopic());
    }
}
