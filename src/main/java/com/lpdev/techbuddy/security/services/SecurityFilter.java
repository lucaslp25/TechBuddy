package com.lpdev.techbuddy.security.services;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lpdev.techbuddy.exceptions.TechBuddySecurityException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    public SecurityFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var token = this.recoverToken(request); //request do cliente

        if (token != null) {

            try{
                DecodedJWT decodedJWT = tokenService.tokenValidate(token);

                String login = decodedJWT.getSubject();
                List<String> roles = decodedJWT.getClaim("roles").asList(String.class);

                List<SimpleGrantedAuthority> authorities =

                        roles
                                .stream()
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList());

                var auth = new UsernamePasswordAuthenticationToken(login, null, authorities);
                //aqui faz um auth que contem o: login com token validado, null, e as permissões desse usuario.

                SecurityContextHolder.getContext().setAuthentication(auth);

            }catch (JWTVerificationException | TechBuddySecurityException lp){
                log.error("Security exception: falha na validação do token, camada de filtro..." + lp.getMessage());

                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }

    public String recoverToken(HttpServletRequest request){

        var header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }
        return header.replace("Bearer ", "");
    }
}
