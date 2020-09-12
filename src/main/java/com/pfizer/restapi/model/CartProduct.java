package com.pfizer.restapi.model;

import javax.persistence.*;

@Entity
public class CartProduct {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name= "cart_id")
    private Cart cart;
    @ManyToOne
    @JoinColumn(name= "product_id")
    private Product product;

}
