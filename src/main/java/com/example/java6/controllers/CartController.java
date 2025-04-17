package com.example.java6.controllers;

import com.example.java6.entities.Product;
import com.example.java6.services.CartService;
import com.example.java6.services.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // Hiển thị giỏ hàng
    @GetMapping
    public String viewCart(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/auth/login"; // Chuyển hướng tới trang login nếu người dùng chưa đăng nhập
        }

        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("total", cartService.getTotal());
        return "user/home/cart";
    }

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable int id, RedirectAttributes redirectAttributes, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/auth/login"; // Nếu người dùng chưa đăng nhập, chuyển hướng đến trang login
        }

        Product product = productService.getProductById(id);
        if (product != null) {
            cartService.addToCart(product);
            redirectAttributes.addFlashAttribute("message", "Sản phẩm đã được thêm vào giỏ hàng!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Sản phẩm không tồn tại!");
        }
        return "redirect:/user/cart";
    }

    // Cập nhật số lượng sản phẩm trong giỏ hàng
    @PostMapping("/update/{id}")
    public String updateQuantity(@PathVariable int id, @RequestParam int quantity, RedirectAttributes redirectAttributes, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/auth/login"; // Nếu người dùng chưa đăng nhập, chuyển hướng đến trang login
        }

        if (quantity > 0) {
            cartService.updateQuantity(id, quantity);
            redirectAttributes.addFlashAttribute("message", "Cập nhật số lượng thành công!");
        } else {
            cartService.removeItem(id);
            redirectAttributes.addFlashAttribute("message", "Sản phẩm đã được xóa khỏi giỏ hàng!");
        }
        return "redirect:/user/cart";
    }

    // Xóa sản phẩm khỏi giỏ hàng
    @PostMapping("/remove/{id}")
    public String removeItem(@PathVariable int id, RedirectAttributes redirectAttributes, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/auth/login"; // Nếu người dùng chưa đăng nhập, chuyển hướng đến trang login
        }

        cartService.removeItem(id);
        redirectAttributes.addFlashAttribute("message", "Sản phẩm đã bị xóa!");
        return "redirect:/user/cart";
    }

    // Làm trống giỏ hàng
    @PostMapping("/clear")
    public String clearCart(RedirectAttributes redirectAttributes, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/auth/login"; // Nếu người dùng chưa đăng nhập, chuyển hướng đến trang login
        }

        cartService.clearCart();
        redirectAttributes.addFlashAttribute("message", "Giỏ hàng đã được làm trống!");
        return "redirect:/user/cart";
    }
}
