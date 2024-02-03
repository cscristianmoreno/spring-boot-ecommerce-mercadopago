package com.ecommerce.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {
    
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAuthenticationManager customAuthenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomOncePerRequestFilter customOncePerRequestFilter;

    public SecurityConfig(final CustomAuthenticationEntryPoint customAuthenticationEntryPoint, final CustomAuthenticationManager customAuthenticationManager,
    final CustomUserDetailsService customUserDetailsService, CustomOncePerRequestFilter customOncePerRequestFilter) {
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAuthenticationManager = customAuthenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.customOncePerRequestFilter = customOncePerRequestFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return (
            httpSecurity
            .authorizeHttpRequests((request) -> {
                request.requestMatchers("/users/**").permitAll();
                request.requestMatchers("/products/**").permitAll();
                request.requestMatchers("/shopping/**").permitAll();
                request.requestMatchers("/cards/**").permitAll();
                request.anyRequest().authenticated();
            })
            .csrf((csrf) -> {
                csrf.disable();
            })
            .exceptionHandling((exception) -> {
                exception.authenticationEntryPoint(customAuthenticationEntryPoint);
            })
            .authenticationManager(customAuthenticationManager)
            .addFilterBefore(customOncePerRequestFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement((session) -> {
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            })
            .authenticationProvider(authenticationProvider())
            .httpBasic(Customizer.withDefaults())
            .build()    
        );
    }

    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);

        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    WebMvcConfigurer webMvcConfigurer() {
        return new CustomWebMvcConfigurer();
    }
}
