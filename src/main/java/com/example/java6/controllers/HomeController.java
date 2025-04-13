package com.example.java6.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.java6.entities.Product;
import com.example.java6.services.ProductService;

@Controller
@RequestMapping
public class HomeController {

    @Autowired
    private ProductService productService;

    // Trang admin - hiện tên người đăng nhập
    @GetMapping("/home")
    public String adminHome(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails userDetails) {
                model.addAttribute("username", userDetails.getUsername());
            }
        }
        return "user/home/indexUser"; // Hoặc return "admin/home/index" nếu bạn tách giao diện admin
    }

    // Trang chính hiển thị sản phẩm + flash sale
    @GetMapping
    public String listProducts(Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 6;
        Pageable pageable = PageRequest.of(page, pageSize);

        // Lấy danh sách sản phẩm flash sale
        List<Product> flashSaleProducts = productService.getFlashSaleProducts();

        // Lấy toàn bộ sản phẩm
        List<Product> allProducts = productService.findAll();

        // Lọc ra sản phẩm thường (không nằm trong flash sale)
        List<Product> normalProducts = allProducts.stream()
                .filter(p -> !flashSaleProducts.contains(p))
                .collect(Collectors.toList());

        // Áp dụng phân trang cho sản phẩm thường
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageSize), normalProducts.size());

        // Kiểm tra trường hợp không có sản phẩm
        List<Product> pageContent = start < end ? normalProducts.subList(start, end) : List.of();

        Page<Product> normalProductPage = new PageImpl<>(pageContent, pageable, normalProducts.size());

        model.addAttribute("flashSale", flashSaleProducts);
        model.addAttribute("page", normalProductPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", normalProductPage.getTotalPages());

        return "user/home/indexUser";
    }
}
