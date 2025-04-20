package com.example.java6.controllers;

import com.example.java6.dto.CartItemDTO;
import com.example.java6.entities.*;
import com.example.java6.repositories.*;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final AccountRepository accountRepo;
    private final OrderRepository orderRepo;
    private final OrderDetailRepository orderDetailRepo;

    @GetMapping
    public String listProducts(Model model,
            @RequestParam(defaultValue = "0") int page,
            Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails userDetails) {
                model.addAttribute("username", userDetails.getUsername());

                for (GrantedAuthority authority : userDetails.getAuthorities()) {
                    if (authority.getAuthority().equals("ROLE_ADMIN")) {
                        return "redirect:/admin/home";
                    }
                }
            }
        }
        return "redirect:/order";
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam("address") String address,
            @AuthenticationPrincipal(expression = "username") String username,
            HttpSession session,
            Model model) {

        @SuppressWarnings("unchecked")
        List<CartItemDTO> cartItems = (List<CartItemDTO>) session.getAttribute("cartItems");

        if (cartItems == null || cartItems.isEmpty()) {
            model.addAttribute("error", "Giỏ hàng đang trống!");
            return "redirect:/user/cart";
        }

        Account account = accountRepo.findById(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản"));

        // Tạo đơn hàng
        Order order = new Order();
        order.setAccount(account);
        order.setAddress(address);
        order.setCreateDate(new Date());
        order = orderRepo.save(order);

        // Lưu chi tiết đơn hàng
        for (CartItemDTO item : cartItems) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(item.getProduct());
            detail.setPrice(item.getProduct().getPrice());
            detail.setQuantity(item.getQuantity());
            orderDetailRepo.save(detail);
        }

        // Xóa giỏ hàng khỏi session
        session.removeAttribute("cartItems");

        model.addAttribute("message", "Thanh toán thành công!");
        return "redirect:/user/orders"; // Chuyển hướng đến trang lịch sử đơn hàng
    }
}
