package com.ecommerce.app.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecommerce.app.auth.LoginAuth;
import com.ecommerce.app.auth.TokenAuth;
import com.ecommerce.app.dao.UsersDAO;
import com.ecommerce.app.dto.UsersDTO;
import com.ecommerce.app.entity.Users;
import com.ecommerce.app.security.CustomAuthenticationManager;

@Controller
@ResponseBody
@RequestMapping(value = "/users")
public class UserController {
    
    private final UsersDAO userDAO;
    private final CustomAuthenticationManager customAuthenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    
    public UserController(final UsersDAO userDAO, final CustomAuthenticationManager customAuthenticationManager, 
        final PasswordEncoder passwordEncoder, final ModelMapper modelMapper) {
        this.userDAO = userDAO;
        this.customAuthenticationManager = customAuthenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody Users user) {
        System.out.println("LLegó acá");
        String securePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(securePassword);

        this.userDAO.create(user);
        return new ResponseEntity<String>("Usuario creado con exito", HttpStatus.OK);
    }

    @PostMapping("/login")
    public TokenAuth login(@RequestBody LoginAuth loginAuth) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginAuth.getEmail(), loginAuth.getPassword()); 
        customAuthenticationManager.authenticate(authentication);

        TokenAuth tokenAuth = this.userDAO.login(loginAuth.getEmail());
        return tokenAuth;
    } 

    @PostMapping("/authentication")
    @PreAuthorize("ROLE_USER")
    public UsersDTO userDTO(@RequestAttribute Users user) {

        UsersDTO userDTO = modelMapper.map(user, UsersDTO.class);
        System.out.println(userDTO);
        return userDTO;
    }
}
