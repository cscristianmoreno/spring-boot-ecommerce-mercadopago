package com.ecommerce.app.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Shopping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(referencedColumnName = "id", name = "user_id")
    private Users id_user;

    @OneToOne
    @JoinColumn(referencedColumnName = "id", name = "product_id")
    private Products id_product;

    @Temporal(TemporalType.TIME)
    private Date date;
}
