package com.ecommerce.app.auth;

import com.ecommerce.app.dto.UsersDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TokenAuth {
    private String token;
    private UsersDTO user;  
}
