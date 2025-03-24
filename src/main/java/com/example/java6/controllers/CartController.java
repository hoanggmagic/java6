package com.example.java6.controllers;

import com.example.java6.entities.Product;
import com.example.java6.services.CartService;
import com.example.java6.services.ProductService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user/cart")
@SessionAttributes("cart")
public class CartController {
    private final CartService cartService;
    private final ProductService productService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("total", cartService.getTotal());
        return "user/home/cart";
    }

    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable int id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            cartService.addToCart(product);
        }
        return "redirect:/user/cart";
    }

    @PostMapping("/update/{id}")
    public String updateQuantity(@PathVariable int id, @RequestParam int quantity) {
        cartService.updateQuantity(id, quantity);
        return "redirect:/user/cart";
    }

    @PostMapping("/remove/{id}")
    public String removeItem(@PathVariable int id) {
        cartService.removeItem(id);
        return "redirect:/user/cart";
    }

    @PostMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/user/cart";
    }
}
