package com.ProjetoDSbancario.Projeto_DS.filters;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ProjetoDSbancario.Projeto_DS.configurations.SecurityConfiguration;
import com.ProjetoDSbancario.Projeto_DS.models.authentication.User;
import com.ProjetoDSbancario.Projeto_DS.models.authentication.UserDetailsImpl;
import com.ProjetoDSbancario.Projeto_DS.repositories.UserRepository;
import com.ProjetoDSbancario.Projeto_DS.services.authentication.JwtTokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenService jwtTokenService;
    private UserRepository userRepository;

    UserAuthenticationFilter(JwtTokenService jwtTokenService, UserRepository userRepository){
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (checkIfEndpointIsNotPublic(request)) {
            String token = recoveryToken(request); 

            if (token != null) {
                String subject = jwtTokenService.getSubjectFromToken(token); 
                User user = userRepository.findByCpf(subject).get(); 
                UserDetailsImpl userDetails = new UserDetailsImpl(user); 

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // define o objeto de autenticação no contexto de segurança do Spring Security
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } 
        }
        
        filterChain.doFilter(request, response); // continua o processamento da req
    }

    private String recoveryToken(HttpServletRequest request) {

        String token = request.getHeader("Authorization");

        if (token == null) return token;
        
        token = token.replace("Bearer","").trim();

        return token;
    }

    // verifica se o endpoint requer autenticacao antes de processar a requisicao
    private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return !Arrays.asList(SecurityConfiguration.UNAUTHORIZED_ENDPOINTS).contains(requestURI);
    }
}