package com.example.java6.controllers;

import com.example.java6.entities.Account;
import com.example.java6.entities.Product;
import com.example.java6.entities.CartItem;
import com.example.java6.services.AccountService;
import com.example.java6.services.CartService;
import com.example.java6.services.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user/cart")
public class CartController {

    private final CartService cartService;
    private final ProductService productService;
    private final AccountService accountService;

    public CartController(CartService cartService, ProductService productService, AccountService accountService) {
        this.cartService = cartService;
        this.productService = productService;
        this.accountService = accountService;
    }

    // Hiển thị giỏ hàng
    @GetMapping
    public String viewCart(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/auth/login";
        }

        Account currentUser = accountService.getCurrentUser();

        List<CartItem> items = cartService.getCartItems(currentUser);
        double total = items.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();

        model.addAttribute("cartItems", items);
        model.addAttribute("total", total);

        return "user/home/cart"; // Thymeleaf template
    }

    // Thêm sản phẩm vào giỏ
    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable("id") Integer productId,
                            @RequestParam(defaultValue = "1") int quantity,
                            RedirectAttributes redirectAttributes,
                            Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/auth/login";
        }

        Product product = productService.getProductById(productId);
        if (product == null) {
            redirectAttributes.addFlashAttribute("error", "Sản phẩm không tồn tại!");
            return "redirect:/user/cart";
        }

        Account currentUser = accountService.getCurrentUser();
        cartService.addToCart(currentUser, productId, quantity);

        redirectAttributes.addFlashAttribute("message", "Đã thêm sản phẩm vào giỏ hàng!");
        return "redirect:/user/cart";
    }

    // Cập nhật số lượng
    @PostMapping("/update/{itemId}")
    public String updateQuantity(@PathVariable("itemId") Long itemId,
                                 @RequestParam int quantity,
                                 RedirectAttributes redirectAttributes,
                                 Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/auth/login";
        }

        Account currentUser = accountService.getCurrentUser();

        if (quantity <= 0) {
            cartService.removeItem(currentUser, itemId);
            redirectAttributes.addFlashAttribute("message", "Đã xóa sản phẩm khỏi giỏ!");
        } else {
            // Bạn có thể tạo thêm method updateQuantity nếu muốn cụ thể hơn
            // Còn hiện tại gọi remove & add lại là cách đơn giản
            CartItem item = cartService.getCartItems(currentUser)
                    .stream()
                    .filter(i -> i.getId().equals(itemId))
                    .findFirst().orElse(null);

            if (item != null) {
                cartService.removeItem(currentUser, itemId);
                cartService.addToCart(currentUser, item.getProduct().getId(), quantity);
                redirectAttributes.addFlashAttribute("message", "Đã cập nhật số lượng!");
            }
        }

        return "redirect:/user/cart";
    }

    // Xóa sản phẩm
    @PostMapping("/remove/{itemId}")
    public String removeItem(@PathVariable("itemId") Long itemId,
                             RedirectAttributes redirectAttributes,
                             Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/auth/login";
        }

        Account currentUser = accountService.getCurrentUser();
        cartService.removeItem(currentUser, itemId);
        redirectAttributes.addFlashAttribute("message", "Sản phẩm đã bị xóa!");
        return "redirect:/user/home/cart";
    }

    // Xóa toàn bộ giỏ hàng
    @PostMapping("/clear")
    public String clearCart(RedirectAttributes redirectAttributes,
                            Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/auth/login";
        }

        Account currentUser = accountService.getCurrentUser();
        cartService.clearCart(currentUser);
        redirectAttributes.addFlashAttribute("message", "Giỏ hàng đã được làm trống!");
        return "redirect:/user/homehome/cart";
    }
}
