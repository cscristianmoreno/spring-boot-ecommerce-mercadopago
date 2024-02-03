package com.ecommerce.app.mercadopago;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ecommerce.app.dto.CardDTO;
import com.mercadopago.client.cardtoken.CardTokenClient;
import com.mercadopago.client.cardtoken.CardTokenRequest;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;

@Service
public class MpController {
    @Value("${mp.secret-key}")
    private String token;

    public void createToken() throws MPException, MPApiException {

        MPRequestOptions mpRequestOptions = MPRequestOptions.builder().accessToken(token).build();
        
        CardTokenRequest cardTokenRequest = CardTokenRequest
        .builder()
            .cardId("5031755734530604")
            .securityCode("123")
            .customerId("Test")
        .build();

        CardTokenClient cardTokenClient = new CardTokenClient();
        cardTokenClient.create(cardTokenRequest, mpRequestOptions);

        System.out.println(cardTokenRequest);
    }

    public Payment createPayment(final CardDTO cardDTO) throws MPException, MPApiException {
        Map<String, String> headers = new HashMap<String, String>();

        UUID uuid = UUID.randomUUID();
        headers.put("X-Idempotency-key", uuid.toString());

        MPRequestOptions mpRequestOptions = MPRequestOptions.builder().customHeaders(headers).accessToken(token).build();

        PaymentPayerRequest paymentPayerRequest = PaymentPayerRequest
        .builder()
            .identification(
                IdentificationRequest.builder().number(cardDTO.getIdentificationNumber()).type(cardDTO.getIdentificationType()).build()
            )
            .email(cardDTO.getCardHolderEmail())
            .firstName(cardDTO.getCardHolderName())
            .entityType("individual")
            .type("customer")
        .build();

        PaymentCreateRequest paymentCreateRequest = PaymentCreateRequest
        .builder()
            .transactionAmount(new BigDecimal(cardDTO.getAmount()))
            .token(cardDTO.getToken())
            .description("Producto")
            .installments(cardDTO.getInstallments())
            .paymentMethodId(cardDTO.getPaymentMethodId())
            .payer(paymentPayerRequest)
            .issuerId(cardDTO.getIssuerId())
            // .callbackUrl("https://payment.com/check/payment")
            .processingMode(cardDTO.getProccessingMode())
        .build();

        System.out.println(paymentCreateRequest.toString());

        PaymentClient paymentClient = new PaymentClient();
        Payment payment = paymentClient.create(paymentCreateRequest, mpRequestOptions);

        return payment;
    }
}
