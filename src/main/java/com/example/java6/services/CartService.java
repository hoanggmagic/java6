package com.example.java6.services;

import com.example.java6.dto.CartItemDTO;
import com.example.java6.entities.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private List<CartItemDTO> cartItems = new ArrayList<>();

    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }

    public void addToCart(Product product) {
        Optional<CartItemDTO> existingItem = cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + 1);
        } else {
            cartItems.add(new CartItemDTO(product, 1));
        }
    }

    public void updateQuantity(int productId, int quantity) {
        cartItems.forEach(item -> {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(quantity);
            }
        });
    }

    public void removeItem(int productId) {
        cartItems.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    public void clearCart() {
        cartItems.clear();
    }

    public double getTotal() {
        return cartItems.stream().mapToDouble(CartItemDTO::getTotalPrice).sum();
    }

}
