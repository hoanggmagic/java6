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
public class HomeController {

    @Autowired
    private ProductService productService;

    @GetMapping({ "/home" })
    public String showHomePage(Model model, @RequestParam(defaultValue = "0") int page,
            Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails userDetails) {
                model.addAttribute("username", userDetails.getUsername());

                // 💡 Kiểm tra nếu là ADMIN thì chuyển trang
                if (authentication.getAuthorities().stream()
                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                    return "redirect:/admin/home";
                }
            }
        }

        // Các xử lý cho user bình thường
        List<Product> flashSaleProducts = productService.getFlashSaleProducts();

        List<Product> allProducts = productService.findAll();
        List<Product> normalProducts = allProducts.stream()
                .filter(p -> !flashSaleProducts.contains(p))
                .collect(Collectors.toList());

        int pageSize = 6;
        Pageable pageable = PageRequest.of(page, pageSize);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageSize), normalProducts.size());
        List<Product> currentPageList = start < end ? normalProducts.subList(start, end) : List.of();
        Page<Product> productPage = new PageImpl<>(currentPageList, pageable, normalProducts.size());

        model.addAttribute("flashSale", flashSaleProducts);
        model.addAttribute("page", productPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());

        return "user/home/indexUser"; // ✅ chỉ user mới vào đây
    }

}
