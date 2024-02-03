package com.ecommerce.app.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UsersDTO {
    
    private int id;
    private String name;
    private String lastname;
    private String email;
}
