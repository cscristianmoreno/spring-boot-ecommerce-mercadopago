package com.ecommerce.app.services;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperService {
    
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
