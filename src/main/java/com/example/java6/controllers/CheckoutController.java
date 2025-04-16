package com.example.java6.controllers;

import com.example.java6.entities.Order;
import com.example.java6.entities.OrderDetail;
import com.example.java6.entities.Product;
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

    @GetMapping
    public String checkoutForm(Model model) {
        model.addAttribute("total", cartService.getTotal());
        return "user/home/checkout"; // View checkout form
    }

    @PostMapping
    public String processCheckout(@RequestParam("address") String address,
                                   Principal principal,
                                   RedirectAttributes redirectAttributes,
                                   HttpSession session) {
        try {
            String username = principal.getName(); // Lấy username đang đăng nhập

            // Lấy danh sách sản phẩm từ cart
            List<OrderDetail> orderDetails = new ArrayList<>();
            for (var item : cartService.getCartItems()) {
                OrderDetail detail = new OrderDetail();
                detail.setProduct(item.getProduct());
                detail.setPrice(item.getProduct().getPrice());
                detail.setQuantity(item.getQuantity());
                orderDetails.add(detail);
            }

            // Gọi service tạo order
            orderService.createOrder(username, address, orderDetails);

            cartService.clearCart(); // Xóa giỏ hàng sau khi đặt

            redirectAttributes.addFlashAttribute("message", "Đặt hàng thành công!");
            return "redirect:/user/cart";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi khi đặt hàng: " + e.getMessage());
            return "redirect:/checkout";
        }
    }
}
