package com.lpdev.techbuddy.controllers;

import com.lpdev.techbuddy.dto.MentorshipRequestCreateDTO;
import com.lpdev.techbuddy.dto.MentorshipRequestResponseDTO;
import com.lpdev.techbuddy.services.MentorshipRequestService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

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

    @PatchMapping(value = "/accept/{id}")
    @PreAuthorize("hasRole('MENTOR_BUDDY')")
    public ResponseEntity<Void> acceptRequest(@PathVariable Long id){

        mentorshipRequestService.acceptRequest(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/myRequests")
    public ResponseEntity<Set<MentorshipRequestResponseDTO>> getMyRequests(){

        Set<MentorshipRequestResponseDTO> dtos = mentorshipRequestService.findMyRequests();

        return ResponseEntity.ok().body(dtos);
    }
}
