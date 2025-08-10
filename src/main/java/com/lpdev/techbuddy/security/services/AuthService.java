package com.lpdev.techbuddy.security.services;

import com.lpdev.techbuddy.exceptions.TechBuddyConflictException;
import com.lpdev.techbuddy.exceptions.TechBuddyNotFoundException;
import com.lpdev.techbuddy.exceptions.TechBuddySecurityException;
import com.lpdev.techbuddy.model.entities.User;
import com.lpdev.techbuddy.model.enums.UserRole;
import com.lpdev.techbuddy.repositories.UserRepository;
import com.lpdev.techbuddy.security.dto.LoginDTO;
import com.lpdev.techbuddy.security.dto.LoginResponseDTO;
import com.lpdev.techbuddy.security.dto.RegisterDTO;
import com.lpdev.techbuddy.security.dto.RegisterResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Transactional
    public LoginResponseDTO login(LoginDTO dtoref){

        //grande aprendizado aqui: NÃO devo retornar esse erro, pois é uma falha de segurança da minha API fazer isso.
       // User user = userRepository.findUserByEmail(dtoref.email()).orElseThrow(()-> new TechBuddyNotFoundException("Não foi encontrado esse email em nosso sistema."));

        var usernamePassword = new UsernamePasswordAuthenticationToken(dtoref.email(), dtoref.password());

        Authentication auth = authenticationManager.authenticate(usernamePassword);

        var authenticadeUser = (User) auth.getPrincipal();

        String token = tokenService.generateToken(authenticadeUser);

        return new LoginResponseDTO(token, authenticadeUser);
    }

    @Transactional
    public RegisterResponseDTO register(RegisterDTO dtoref){

        if (dtoref.email() == null || dtoref.password() == null){
            throw new TechBuddySecurityException("Email e/ou password vazios!");
        }

        if (dtoref.role() != UserRole.DEV_BUDDY && dtoref.role() != UserRole.MENTOR_BUDDY){
            throw new TechBuddySecurityException("Role inválida!");
        }

//        if (userRepository.findUserByEmail(dtoref.email()).isPresent()){
//            throw new TechBuddyConflictException("O email: " + dtoref.email() + " ja está cadastrado no sistema!");
//        }
        //aqui é a mesma situação do login, deixarei comentado para lembrar


        String pass = passwordEncoder.encode(dtoref.password());

        User user = new User(dtoref.name(), dtoref.email(), pass,dtoref.role());

        user = userRepository.save(user);

        return new RegisterResponseDTO(user);
    }

}
