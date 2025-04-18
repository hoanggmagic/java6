package com.example.java6.controllers;

import com.example.java6.entities.Product;
import com.example.java6.services.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Hiển thị danh sách sản phẩm có phân trang
    @GetMapping("/get")
    public String listProducts(Model model,
                               @RequestParam(defaultValue = "0") int page,
                               Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails userDetails) {
                model.addAttribute("username", userDetails.getUsername()); // Gửi username ra giao diện

                // Kiểm tra quyền người dùng
                for (GrantedAuthority authority : userDetails.getAuthorities()) {
                    if (authority.getAuthority().equals("ROLE_ADMIN")) {
                        // Chuyển hướng đến trang Admin nếu là Admin
                        return "admin/home/products";
                    }
                }
            }
        }

        // Nếu không phải Admin, hiển thị trang sản phẩm cho người dùng thường
        int pageSize = 6;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Product> productPage = productService.findAll(pageable);

        int totalPages = productPage.getTotalPages();
        if (page < 0) page = 0;
        if (page >= totalPages) page = totalPages - 1;

        model.addAttribute("productPage", productPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "user/home/products";
    }

    // Hiển thị chi tiết sản phẩm
    @GetMapping("/detail/{id}")
    public String showProductDetail(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails userDetails) {
                model.addAttribute("username", userDetails.getUsername());

                for (GrantedAuthority authority : userDetails.getAuthorities()) {
                    if (authority.getAuthority().equals("ROLE_ADMIN")) {
                        Product product = productService.findById(id);
                        if (product == null) {
                            return "redirect:/products/get";
                        }
                        model.addAttribute("product", product);
                        return "admin/home/product-detail";
                    }
                }
            }
        }

        // Nếu là User, hiển thị trang chi tiết sản phẩm cho người dùng
        Product product = productService.findById(id);
        if (product == null) {
            return "redirect:/products/get";
        }
        model.addAttribute("product", product);
        return "user/home/product-detail-page";
    }
}
