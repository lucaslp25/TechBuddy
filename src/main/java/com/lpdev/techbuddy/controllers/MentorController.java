package com.lpdev.techbuddy.controllers;

import com.lpdev.techbuddy.dto.MentorProfileViewDTO;
import com.lpdev.techbuddy.dto.MentorSearchCriteriaDTO;
import com.lpdev.techbuddy.exceptions.TechBuddyNotFoundException;
import com.lpdev.techbuddy.services.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "/mentor")
public class MentorController {

    private final UserProfileService userProfileService;

    public MentorController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping(value = "/stack")
    public ResponseEntity<Set<MentorProfileViewDTO>> findAllMentorsByStack(@RequestParam String stack) {

        if (stack == null || stack.isEmpty()) {
            throw new TechBuddyNotFoundException("Nenhum mentor encontrado. Os parâmetros estão nulos.");
        }

        Set<MentorProfileViewDTO> dtos = userProfileService.findMentorsProfileByStack(stack);
        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping
    public ResponseEntity<Set<MentorProfileViewDTO>> findAllMentors() {

        Set<MentorProfileViewDTO> dtos = userProfileService.findAllMentorProfiles();
        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping(value = "/available")
    public ResponseEntity<Set<MentorProfileViewDTO>> findAllAvailableMentors() {

        Set<MentorProfileViewDTO> dtos = userProfileService.findAllAvailableMentorProfiles();
        return ResponseEntity.ok().body(dtos);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<MentorProfileViewDTO> findMentorByUsername(@PathVariable String username) {

        MentorProfileViewDTO dto = userProfileService.findMentorProfileByUsername(username);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/criteria")
    public ResponseEntity<Set<MentorProfileViewDTO>> findMentorByCriteria(@RequestBody MentorSearchCriteriaDTO dtoRef) {

        Set<MentorProfileViewDTO> dtos = userProfileService.findMentorByCriteria(dtoRef);
        return ResponseEntity.ok().body(dtos);
    }

}
