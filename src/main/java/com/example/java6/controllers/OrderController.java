package com.example.java6.controllers;
// package com.example.lab6.controllers;

// import com.example.lab6.entities.Order;
// import com.example.lab6.entities.OrderDetail;
// import com.example.lab6.services.OrderService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @Controller
// @RequestMapping("/checkout")
// public class OrderController {

// @Autowired
// private OrderService orderService;

// @GetMapping
// public String checkoutForm(Model model) {
// model.addAttribute("order", new Order());
// return "checkout";
// }

// @PostMapping
// public String checkout(@RequestParam String username,
// @RequestParam String address,
// @RequestParam List<OrderDetail> orderDetails,
// Model model) {
// try {
// Order order = orderService.createOrder(username, address, orderDetails);
// model.addAttribute("order", order);
// return "checkout_success";
// } catch (Exception e) {
// model.addAttribute("error", e.getMessage());
// return "checkout";
// }
// }
// }
