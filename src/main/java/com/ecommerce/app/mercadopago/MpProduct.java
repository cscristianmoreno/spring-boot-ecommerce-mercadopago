package com.ecommerce.app.mercadopago;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MpProduct {
 
    private int id;
    private String title;
    private String image;
    private String category;
    private Long price;
    private int quantity;
}
