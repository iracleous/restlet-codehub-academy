package com.pfizer.restapi.representation;

import lombok.Data;

@Data
public class CartProductionRepresentation {
    private String customerName;
    private long cartId;
    private String productName;
    private int productQuantity;
}
