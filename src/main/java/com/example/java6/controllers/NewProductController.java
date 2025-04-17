package com.example.java6.controllers;

import com.example.java6.entities.Product;
import com.example.java6.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class NewProductController {

    @Autowired
    private ProductService productService;

    // Hiển thị danh sách sản phẩm mới nhất
    @GetMapping("/new-products")
    public String showNewProducts(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails userDetails) {
                model.addAttribute("username", userDetails.getUsername()); // Gửi username ra giao diện

                // Kiểm tra quyền người dùng và chuyển hướng nếu là Admin
                for (GrantedAuthority authority : userDetails.getAuthorities()) {
                    if (authority.getAuthority().equals("ROLE_ADMIN")) {
                        // Chuyển hướng đến trang Admin nếu là Admin
                        return "admin/home/new-products"; // Trang sản phẩm mới nhất của Admin
                    }
                }
            }
        }

        // Nếu không phải Admin, hiển thị trang sản phẩm mới nhất cho người dùng
        List<Product> newProducts = productService.getNewProducts();
        model.addAttribute("products", newProducts);
        return "user/home/new-products";  // Trang sản phẩm mới nhất cho User
    }

    // Hiển thị chi tiết sản phẩm
    @GetMapping("/product/{id}")
    public String showProductDetail(@PathVariable("id") Integer id, Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails userDetails) {
                model.addAttribute("username", userDetails.getUsername()); // Gửi username ra giao diện

                // Kiểm tra quyền người dùng và chuyển hướng nếu là Admin
                for (GrantedAuthority authority : userDetails.getAuthorities()) {
                    if (authority.getAuthority().equals("ROLE_ADMIN")) {
                        // Chuyển hướng đến trang chi tiết sản phẩm của Admin
                        return "admin/home/product-detail"; // Trang chi tiết sản phẩm của Admin
                    }
                }
            }
        }

        // Hiển thị chi tiết sản phẩm cho người dùng
        Product product = productService.findById(id);
        if (product == null) {
            // Nếu không tìm thấy sản phẩm, chuyển hướng về trang sản phẩm mới nhất
            return "redirect:/new-products";
        }
        model.addAttribute("product", product);
        return "user/home/product-detail-page"; // Trang chi tiết sản phẩm cho User
    }
}
