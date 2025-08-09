package com.lpdev.techbuddy.security.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lpdev.techbuddy.exceptions.TechBuddySecurityException;
import com.lpdev.techbuddy.model.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private final String ISSUER = "TECH-BUDDY-API";

    public String generateToken(User user){

        try{
            Algorithm algorithm = Algorithm.HMAC256(secret); //meu padrão de algoritmo por enquanto

            List<String> userRoles = user.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return
                    JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getEmail())
                    .withExpiresAt(expiresTime())
                    .withClaim("roles", userRoles)
                    .sign(algorithm);

        }catch (JWTCreationException lp){
            throw new TechBuddySecurityException("Geração de token JWT falhou." + lp.getMessage());
        }
    }

    public DecodedJWT tokenValidate(String token){

        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token);

        }catch (JWTVerificationException lp){
            throw new TechBuddySecurityException("Validação de token JWT falhou." + lp.getMessage()); //ou retornar um "";
        }
    }

    private Instant expiresTime() {
        return LocalDateTime.now().plusMinutes(90).toInstant(ZoneOffset.of("-03:00"));
    }

}
