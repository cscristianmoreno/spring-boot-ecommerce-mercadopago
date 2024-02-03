package com.ecommerce.app.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentResponseDTO {
    private Long id;
    private String status;
    private String detail;    
}
