package com.ecommerce.app.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CardDTO {
    private int amount;
    private String cardHolderName;
    private String cardHolderEmail;
    private String identificationNumber;
    private String identificationType;
    private int installments;
    private String issuerId;
    private String merchantAccountId;
    private String paymentMethodId;
    private String proccessingMode;
    private String token;
}
