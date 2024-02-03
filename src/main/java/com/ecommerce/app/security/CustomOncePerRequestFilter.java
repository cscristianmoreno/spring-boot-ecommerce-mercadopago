package com.ecommerce.app.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ecommerce.app.entity.Users;
import com.ecommerce.app.repository.UserRepository;
import com.ecommerce.app.services.JwtAuthService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CustomOncePerRequestFilter extends OncePerRequestFilter {

    private final JwtAuthService jwtAuthService;
    private final UserRepository userRepository;

    public CustomOncePerRequestFilter(final JwtAuthService jwtAuthService, final UserRepository userRepository) {
        this.jwtAuthService = jwtAuthService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, JwtException {
        String uri = request.getRequestURI();

        if (!uri.contains("/authentication")) {
            filterChain.doFilter(request, response); 
            return;
        }

        String header = request.getHeader("Authorization");

        UserDetails userDetails = jwtAuthService.authentication(header);

        if (userDetails == null) {
            return;
        }
        
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
        
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(usernamePasswordAuthenticationToken);

        Optional<Users> user = this.userRepository.findByEmail(userDetails.getUsername());

        request.setAttribute("user", user.get());
        filterChain.doFilter(request, response);
    }
    
}
