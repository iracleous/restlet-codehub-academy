package com.pfizer.restapi.representation;


import lombok.Data;

@Data
public class ProductRepresentation {
    private String name;
    private double price;
    private int  inventoryQuantity;
    /** The URL of this resource. */
    private String uri;
}