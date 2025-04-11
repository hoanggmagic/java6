package com.example.java6.controllers;

import com.example.java6.entities.Product;
import com.example.java6.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class NewProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/new-products")
    public String showNewProducts(Model model) {
        List<Product> newProducts = productService.getNewProducts();
        model.addAttribute("products", newProducts);
        return "user/home/new-products.html";
    }

    @GetMapping("/product/{id}")
    public String showProductDetail(@PathVariable("id") Integer id, Model model) {
        Product product = productService.findById(id);
        if (product == null) {
            return "redirect:/new-products";
        }
        model.addAttribute("product", product);
        return "user/home/product-detail-page.html";
    }

}
