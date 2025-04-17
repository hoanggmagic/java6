package com.example.java6.services;

import com.example.java6.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Product product;
    private int quantity;

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}
