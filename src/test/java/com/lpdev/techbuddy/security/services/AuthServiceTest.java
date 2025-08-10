package com.lpdev.techbuddy.security.services;

import com.lpdev.techbuddy.model.entities.User;
import com.lpdev.techbuddy.model.enums.UserRole;
import com.lpdev.techbuddy.repositories.UserRepository;
import com.lpdev.techbuddy.security.dto.LoginDTO;
import com.lpdev.techbuddy.security.dto.LoginResponseDTO;
import com.lpdev.techbuddy.security.dto.RegisterDTO;
import com.lpdev.techbuddy.security.dto.RegisterResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("Teste para testar se o funcionamento do login e fluxo esta correto quando: Todos argumentos estão válidos e user ja esta registrado")
    void login_deveRetornarTokenQuandoCredenciaisValidas() {

        LoginDTO loginDTO = new LoginDTO("test@email.com", "123456");
        User mockUser = new User("Test User", loginDTO.email(), "encodedPass", UserRole.DEV_BUDDY);
        String fakeToken = "um.jwt.token.falso.gerado.pelo.mock";

        Authentication fullyAuthenticated = new UsernamePasswordAuthenticationToken(mockUser, null, mockUser.getAuthorities());

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(fullyAuthenticated);

        when(tokenService.generateToken(any(User.class))).thenReturn(fakeToken);

        LoginResponseDTO response = authService.login(loginDTO);

        assertNotNull(response, "A resposta do login não deveria ser nula.");
        assertEquals(fakeToken, response.token(), "O token retornado não é o esperado.");
        assertEquals(mockUser.getEmail(), response.email(), "O email do usuário na resposta está incorreto.");

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService, times(1)).generateToken(mockUser);

    }

    @Test
    @DisplayName("Teste para testar se o funcionamento do register e fluxo esta correto quando: Todos argumentos estão válidos e user ainda não esta registrado.")
    void register() {

        String password = "123456";
        RegisterDTO dto = new RegisterDTO("LucasTest", "lucas@example.com", password, UserRole.DEV_BUDDY);

        String encryptedPassword = "senha_super_secreta_criptografada";

        when(passwordEncoder.encode(password)).thenReturn(encryptedPassword);

        User user = new User(dto.name(), dto.email(), encryptedPassword, UserRole.DEV_BUDDY);

        when(userRepository.save(any(User.class))).thenReturn(user);

        RegisterResponseDTO response = authService.register(dto);

        Assertions.assertNotNull(response);

        Assertions.assertEquals(response.email(), dto.email(), "Os emails devem ser iguais");
        Assertions.assertEquals(response.name(), dto.name(), "Os nomes devem ser iguais");

        verify(passwordEncoder, times(1)).encode(password);
        verify(userRepository, times(1)).save(any(User.class));
    }
}