package com.pfizer.restapi.representation;


import com.pfizer.restapi.model.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductRepresentation {
    private String name;
    private double price;
    private int  inventoryQuantity;
    /** The URL of this resource. */
    private String uri;


    public ProductRepresentation(
            Product product){


    }





}