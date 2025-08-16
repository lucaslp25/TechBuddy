package com.lpdev.techbuddy.utils;

import com.lpdev.techbuddy.exceptions.TechBuddyNotFoundException;
import com.lpdev.techbuddy.model.entities.User;
import com.lpdev.techbuddy.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtils {

    private final UserRepository userRepository;

    public User checkAuth() {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User requesterUser = userRepository.findUserByEmail(userEmail).orElseThrow(()-> new TechBuddyNotFoundException("Usuário não encontrado com email: " + userEmail));

        return requesterUser;
    }

}
