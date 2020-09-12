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
    private int inventoryQuantity;
    /**
     * The URL of this resource.
     */
    private String uri;


    public ProductRepresentation(
            Product product) {
        if (product != null) {
            inventoryQuantity = product.getInventoryQuantity();
            name = product.getName();
            price = product.getPrice();
            uri = "http://localhost:9000/v1/product/" + product.getId();
        }
    }

    public Product createProduct() {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setInventoryQuantity(inventoryQuantity);
        return product;
    }
}



