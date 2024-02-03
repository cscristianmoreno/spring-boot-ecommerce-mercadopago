package com.ecommerce.app.dao;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.ecommerce.app.auth.TokenAuth;
import com.ecommerce.app.dto.UsersDTO;
import com.ecommerce.app.entity.Users;
import com.ecommerce.app.repository.ImplementationRepository;
import com.ecommerce.app.repository.UserRepository;
import com.ecommerce.app.services.JwtService;

@Service
public class UsersDAO implements ImplementationRepository<Users> {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public UsersDAO(final UserRepository userRepository, final ModelMapper modelMapper, final JwtService jwtService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.jwtService = jwtService;
    }

    @Override
    public void create(Users users) {
        this.userRepository.save(users);
    }

    @Override
    public Users read(int id) {
        Optional<Users> user = this.userRepository.findById(id);

        if (user.isEmpty()) {
            return null;
        }

        return user.get();
    }

    @Override
    public void update(Users users) {

    }

    @Override
    public void delete(int id) {
        Optional<Users> user = this.userRepository.findById(id);

        if (user.isEmpty()) {
            return;
        }

        this.userRepository.deleteById(id);
    }
    
    public TokenAuth login(String email) {
        Optional<Users> user = this.userRepository.findByEmail(email);
        
        if (!user.isPresent()) {
            throw new BadCredentialsException("Usuario no encontrado");
        }


        UsersDTO userDTO = modelMapper.map(user, UsersDTO.class);
        String token = jwtService.generateToken(userDTO);

        TokenAuth tokenAuth = TokenAuth.builder().token(token).user(userDTO).build();
        return tokenAuth;
    }

    @Override
    public Page<Users> findAll(Pageable pageable) {
        PageRequest page = PageRequest.of(1, 8, Sort.by("id").ascending());
        Page<Users> users = this.userRepository.findAll(page);
        return users;
    }
}
