package com.pfizer.restapi.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CartProduct {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private int quantity;

    @ManyToOne
    @JoinColumn(name= "cart_id")
    private Cart cart;
    @ManyToOne
    @JoinColumn(name= "product_id")
    private Product product;

}
