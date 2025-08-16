package com.lpdev.techbuddy.controllers;

import com.lpdev.techbuddy.dto.FeedbackCreateDTO;
import com.lpdev.techbuddy.dto.FeedbackResponseDTO;
import com.lpdev.techbuddy.services.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<FeedbackResponseDTO> insertFeedback(@Valid @RequestBody FeedbackCreateDTO dtoRef){

        FeedbackResponseDTO dto = feedbackService.makeFeedback(dtoRef);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.id()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

}
