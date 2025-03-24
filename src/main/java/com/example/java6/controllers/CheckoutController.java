package com.example.java6.controllers;
// package com.example.lab6.controllers;

// import com.example.lab6.dto.CartItemDTO;
// import com.example.lab6.entities.Account;
// import com.example.lab6.entities.Order;
// import com.example.lab6.entities.OrderDetail;
// import com.example.lab6.entities.Product;
// import com.example.lab6.services.CartService;
// import com.example.lab6.services.OrderService;
// import jakarta.servlet.http.HttpSession;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.GetMapping;

// import java.util.Date;
// import java.util.List;

// @Controller
// public class CheckoutController {

// @Autowired
// private CartService cartService;

// @Autowired
// private OrderService orderService;

// @PostMapping("/checkout")
// public String checkout(@RequestParam("address") String address, HttpSession
// session, Model model) {
// // Lấy thông tin tài khoản từ session
// Account account = (Account) session.getAttribute("loggedInUser");
// if (account == null) {
// return "redirect:/login"; // Chuyển hướng đến trang đăng nhập nếu chưa đăng
// nhập
// }

// // Lấy danh sách sản phẩm trong giỏ hàng
// List<CartItemDTO> cartItems = cartService.getCartItems();
// if (cartItems.isEmpty()) {
// model.addAttribute("error", "Giỏ hàng của bạn đang trống!");
// return "redirect:/cart";
// }

// // Tạo đơn hàng mới
// Order order = new Order();
// order.setAddress(address);
// order.setCreateDate(new Date());
// order.setAccount(account);
// order.setOrderDetails(cartItems);

// // Lưu đơn hàng vào database
// orderService.createOrder(order);

// // Xóa giỏ hàng sau khi thanh toán
// cartService.clearCart();

// // Chuyển hướng đến trang xác nhận thanh toán thành công
// return "redirect:/checkout/success";
// }

// @GetMapping("/checkout/success")
// public String checkoutSuccess(Model model) {
// model.addAttribute("message", "Thanh toán thành công! Đơn hàng của bạn đang
// được xử lý.");
// return "user/checkout_success";
// }
// }
