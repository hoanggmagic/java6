package com.example.java6.services;

import com.example.java6.dto.CartItemDTO;
import com.example.java6.entities.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.*;

@Service
@SessionScope
public class ShoppingCartService {

    private Map<Integer, CartItemDTO> cartItems = new HashMap<>();

    public void add(Product product, int quantity) {
        CartItemDTO item = cartItems.get(product.getId());
        if (item == null) {
            item = new CartItemDTO(product, quantity);
        } else {
            item.setQuantity(item.getQuantity() + quantity);
        }
        cartItems.put(product.getId(), item);
    }

    public void remove(int productId) {
        cartItems.remove(productId);
    }

    public void update(int productId, int quantity) {
        CartItemDTO item = cartItems.get(productId);
        if (item != null) {
            item.setQuantity(quantity);
        }
    }

    public void clear() {
        cartItems.clear();
    }

    public Collection<CartItemDTO> getItems() {
        return cartItems.values();
    }

    public int getCount() {
        return cartItems.size();
    }

    public double getTotal() {
        return cartItems.values().stream()
                .mapToDouble(CartItemDTO::getTotalPrice)
                .sum();
    }

}
