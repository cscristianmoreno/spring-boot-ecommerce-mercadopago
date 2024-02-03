package com.ecommerce.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecommerce.app.dto.CardDTO;
import com.ecommerce.app.dto.PaymentResponseDTO;
import com.ecommerce.app.mercadopago.MpController;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;

@Controller
@ResponseBody
@RequestMapping(value = "/cards")
public class CardController {
    
    private final MpController mpController;

    public CardController(final MpController mpController) {
        this.mpController = mpController;
    }

    @PostMapping("/token")
    public void token() throws MPException, MPApiException {
        mpController.createToken();
    }

    @PostMapping("/payment")
    public PaymentResponseDTO create(@RequestBody CardDTO cardDTO) throws Exception {
        Payment payment = mpController.createPayment(cardDTO);
        
        if (payment.getStatus().contains("rejected")) {
            throw new Exception("El pago fue rechazado");
        }
        
        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();
        
        paymentResponseDTO.setId(payment.getId());
        paymentResponseDTO.setStatus(payment.getStatus());
        paymentResponseDTO.setDetail(payment.getStatusDetail());
        
        System.out.println(paymentResponseDTO);
        return paymentResponseDTO;
    }

}
