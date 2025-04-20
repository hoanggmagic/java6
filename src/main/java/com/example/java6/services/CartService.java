package com.example.java6.services;

import com.example.java6.entities.Product;
import com.example.java6.entities.CartItem;

import java.util.List;

public interface CartService {
    void addToCart(String username, Product product);

    void updateQuantity(String username, int productId, int quantity);

    void removeItem(String username, int productId);

    void clearCart(String username);

    List<CartItem> getCartItems(String username);

    double getTotal(String username);
}
