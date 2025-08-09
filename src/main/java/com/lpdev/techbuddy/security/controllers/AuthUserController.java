package com.lpdev.techbuddy.security.controllers;

import com.lpdev.techbuddy.security.dto.LoginDTO;
import com.lpdev.techbuddy.security.dto.LoginResponseDTO;
import com.lpdev.techbuddy.security.dto.RegisterDTO;
import com.lpdev.techbuddy.security.dto.RegisterResponseDTO;
import com.lpdev.techbuddy.security.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthUserController {

    private final AuthService authService;

    public AuthUserController(AuthService authService) {
        this.authService = authService;
    }


    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO dtoRef) {

        LoginResponseDTO loginResponseDTO = authService.login(dtoRef);

        return ResponseEntity.ok().body(loginResponseDTO);
    }


    @PostMapping(value = "/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterDTO dtoRef) {

        RegisterResponseDTO dto = authService.register(dtoRef);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.email()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }


}
