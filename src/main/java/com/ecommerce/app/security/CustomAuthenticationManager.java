package com.ecommerce.app.security;

import java.util.Optional;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.app.entity.Users;
import com.ecommerce.app.repository.UserRepository;

@Service
public class CustomAuthenticationManager implements AuthenticationManager {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationManager(final UserRepository userRepository, @Lazy final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Optional<Users> user = this.userRepository.findByEmail(authentication.getName()); 
        
        if (user.isEmpty()) {
            throw new BadCredentialsException("Credenciales incorrectas");
        }
        
        if (!passwordEncoder.matches(authentication.getCredentials().toString(), user.get().getPassword())) {
            throw new BadCredentialsException("Credenciales incorrectas");
        }

        String username = user.get().getEmail();
        String password = user.get().getPassword();

        Authentication usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        return usernamePasswordAuthenticationToken;
    }
    
}
