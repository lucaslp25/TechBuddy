package com.lpdev.techbuddy.controllers;

import com.lpdev.techbuddy.dto.UpdateDevProfileDTO;
import com.lpdev.techbuddy.dto.UpdateMentorProfileDTO;
import com.lpdev.techbuddy.services.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/profiles")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PutMapping(value = "/dev/me")
    public ResponseEntity<Void> updateMyDevProfile(@Valid @RequestBody UpdateDevProfileDTO dtoRef) {

        userProfileService.updateMyDevProfile(dtoRef);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/mentor/me")
    public ResponseEntity<Void> updateMyMentorProfile(@Valid @RequestBody UpdateMentorProfileDTO dtoRef) {

        userProfileService.updateMyMentorProfile(dtoRef);

        return ResponseEntity.noContent().build();
    }

}
