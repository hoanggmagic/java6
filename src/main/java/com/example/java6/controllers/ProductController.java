package com.example.java6.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.java6.services.ProductService;
import com.example.java6.entities.Product;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String listProducts(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 6; // Số sản phẩm mỗi trang
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Product> productPage = productService.findAll(pageable);
        model.addAttribute("productPage", productPage);

        return "user/home/products"; // Trả về giao diện hiển thị danh sách sản phẩm
    }
}
