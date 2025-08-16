package com.lpdev.techbuddy.controllers;

import com.lpdev.techbuddy.dto.MentorshipSessionResponseDTO;
import com.lpdev.techbuddy.dto.ScheduleSessionDTO;
import com.lpdev.techbuddy.services.MentorshipSessionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/mentorshipSession")
public class MentorshipSessionController {

    private final MentorshipSessionService sessionService;

    public MentorshipSessionController(final MentorshipSessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PatchMapping
    public ResponseEntity<MentorshipSessionResponseDTO> scheduleSession(@Valid @RequestBody ScheduleSessionDTO dtoRef){

        MentorshipSessionResponseDTO dto = sessionService.scheduleSession(dtoRef);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.id()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PatchMapping(value = "/{id}/cancel")
    public ResponseEntity<Void> cancelledSession(@PathVariable Long id){

        sessionService.mentorshipSessionCancelled(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}/complete")
    public ResponseEntity<Void> completedSession(@PathVariable Long id){

        sessionService.mentorshipSessionCompletedStatus(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Set<MentorshipSessionResponseDTO>> findMySessions(){

        Set<MentorshipSessionResponseDTO> sessions = sessionService.findMySessions();

        return ResponseEntity.ok().body(sessions);
    }

}
