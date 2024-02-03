package com.ecommerce.app.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ecommerce.app.security.CustomExceptionHandler;
import com.ecommerce.app.security.CustomUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

@Service
public class JwtAuthService {
    
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomExceptionHandler customExceptionHandler;

    public JwtAuthService(final JwtService jwtService, final CustomUserDetailsService customUserDetailsService, 
    final CustomExceptionHandler customExceptionHandler) {
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
        this.customExceptionHandler = customExceptionHandler;
    } 

    public UserDetails authentication(String header) {
        // System.out.println(header);

        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }

        try {

            String[] token = header.split(" ");
            
            Claims claims = jwtService.decodeToken(token[1]);
            
            String email = (String) claims.get("email");
            
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(email);
            return userDetails;
        }
        catch (JwtException e) {
            customExceptionHandler.jwtException(e);
            return null;
        }
    }
    
}