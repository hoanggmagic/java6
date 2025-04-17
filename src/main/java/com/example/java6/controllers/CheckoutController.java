package com.example.java6.controllers;

import com.example.java6.entities.Account;
import com.example.java6.entities.OrderDetail;
import com.example.java6.repositories.CartItemRepository;
import com.example.java6.entities.CartItem;
import com.example.java6.services.AccountService;
import com.example.java6.services.CartService;
import com.example.java6.services.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CartService cartService;
    private final OrderService orderService;
    private final AccountService accountService;
    private final CartItemRepository cartItemRepository;

    // Hiển thị form thanh toán
    @GetMapping
    public String checkoutForm(Model model, Principal principal, RedirectAttributes redirectAttributes) {
        if (principal == null) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng đăng nhập để tiếp tục thanh toán.");
            return "redirect:/login";
        }

        Account user = accountService.getCurrentUser();
        List<CartItem> cartItems = cartService.getCartItems(user);
        double total = cartItems.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        return "user/home/checkout";
    }

    // Xử lý đặt hàng
    @PostMapping
    public String processCheckout(@RequestParam("address") String address,
                                  Principal principal,
                                  RedirectAttributes redirectAttributes,
                                  HttpSession session) {
        if (principal == null) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng đăng nhập để đặt hàng.");
            return "redirect:/login";
        }

        try {
            Account user = accountService.getCurrentUser();

            List<CartItem> cartItems = cartService.getCartItems(user);
            if (cartItems.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Giỏ hàng của bạn đang trống.");
                return "redirect:/user/cart";
            }

            List<OrderDetail> orderDetails = new ArrayList<>();
            for (CartItem item : cartItems) {
                OrderDetail detail = new OrderDetail();
                detail.setProduct(item.getProduct());
                detail.setPrice(item.getPrice());
                detail.setQuantity(item.getQuantity());
                orderDetails.add(detail);

            }

            // Gọi service tạo đơn hàng
            orderService.createOrder(user.getUsername(), address, orderDetails);

            // Xoá giỏ hàng
           // cartService.clearCart(user);
           cartItemRepository.deleteAll();

            redirectAttributes.addFlashAttribute("message", "Đặt hàng thành công!");
            return "redirect:/user/cart";

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Lỗi khi đặt hàng: " + e.getMessage());
            return "redirect:/checkout";
        }
    }
}
