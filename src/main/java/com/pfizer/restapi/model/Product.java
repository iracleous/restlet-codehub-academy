package com.pfizer.restapi.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;    /** Technical identifier.  primary key */
    private String name;
    private double price;
    private int  inventoryQuantity;
    @OneToMany(mappedBy = "product")
    private List<CartProduct> cartProducts = new ArrayList<>();

}
