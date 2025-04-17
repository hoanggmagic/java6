package com.example.java6.services;

import com.example.java6.dto.CartItemDTO;
import com.example.java6.entities.Account;
import com.example.java6.entities.Cart;
import com.example.java6.entities.CartItem;
import com.example.java6.entities.Product;
import com.example.java6.repositories.CartItemRepository;
import com.example.java6.repositories.CartRepository;
import com.example.java6.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
// CartService.java
@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart getOrCreateCart(Account account) {
        return cartRepository.findByAccount_Username(account.getUsername())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setAccount(account);
                    return cartRepository.save(newCart);
                });
    }

    public void addToCart(Account account, Integer productId, int quantity) {
        Cart cart = getOrCreateCart(account);
        Product product = productRepository.findById(productId).orElseThrow();

        // Check if product is already in cart
        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setPrice(product.getPrice());
            cartItemRepository.save(newItem);
        }
    }

    public List<CartItem> getCartItems(Account account) {
        Cart cart = getOrCreateCart(account);
        return cartItemRepository.findByCart_Id(cart.getId());
    }

    public void removeItem(Account account, Long itemId) {
        Cart cart = getOrCreateCart(account);
        cartItemRepository.deleteById(itemId);
    }

    public void clearCart(Account account) {
        Cart cart = getOrCreateCart(account);
        cartItemRepository.deleteByCart_Id(cart.getId());
    }
}
