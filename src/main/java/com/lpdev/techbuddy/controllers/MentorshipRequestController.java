package com.lpdev.techbuddy.controllers;

import com.lpdev.techbuddy.dto.MentorshipRequestCreateDTO;
import com.lpdev.techbuddy.dto.MentorshipRequestResponseDTO;
import com.lpdev.techbuddy.services.MentorshipRequestService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/mentorshipRequest")
public class MentorshipRequestController {

    private final MentorshipRequestService mentorshipRequestService;

    public MentorshipRequestController(MentorshipRequestService mentorshipRequestService) {
        this.mentorshipRequestService = mentorshipRequestService;
    }

    @PostMapping
    @PreAuthorize("hasRole('DEV_BUDDY')")
    public ResponseEntity<MentorshipRequestResponseDTO> createRequestForMentor(@Valid @RequestBody MentorshipRequestCreateDTO dtoRef){

        MentorshipRequestResponseDTO dto = mentorshipRequestService.createRequestForMentor(dtoRef);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.id()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }
}
