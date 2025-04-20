// package com.example.java6.controllers;

// import com.example.java6.entities.Product;
// import com.example.java6.services.CartService;
// import com.example.java6.services.ProductService;

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// @Controller
// @RequestMapping("/user/cart")
// @SessionAttributes("cart")
// public class CartController {

//     private final CartService cartService;
//     private final ProductService productService;

//     public CartController(CartService cartService, ProductService productService) {
//         this.cartService = cartService;
//         this.productService = productService;
//     }

//     @GetMapping
//     public String listProducts(Model model,
//             @RequestParam(defaultValue = "0") int page,
//             Authentication authentication) {
//         if (authentication != null && authentication.isAuthenticated()) {
//             Object principal = authentication.getPrincipal();
//             if (principal instanceof UserDetails userDetails) {
//                 model.addAttribute("username", userDetails.getUsername());

//                 for (GrantedAuthority authority : userDetails.getAuthorities()) {
//                     if (authority.getAuthority().equals("ROLE_ADMIN")) {
//                         return "redirect:/admin/home";
//                     }
//                 }
//             }
//         }

//         PageRequest pageRequest = PageRequest.of(page, 10);
//         Page<Product> productsPage = productService.findAll(pageRequest);
//         model.addAttribute("products", productsPage.getContent());
//         model.addAttribute("totalPages", productsPage.getTotalPages());
//         model.addAttribute("currentPage", page);

//         model.addAttribute("cartItems", cartService.getCartItems());
//         model.addAttribute("total", cartService.getTotal());

//         return "user/home/cart";
//     }

//     @PostMapping("/add/{id}")
//     public String addToCart(@PathVariable int id, RedirectAttributes redirectAttributes) {
//         Product product = productService.getProductById(id);
//         if (product != null) {
//             cartService.addToCart(product);
//             redirectAttributes.addFlashAttribute("message", "Sản phẩm đã được thêm vào giỏ hàng!");
//         } else {
//             redirectAttributes.addFlashAttribute("error", "Sản phẩm không tồn tại!");
//         }
//         return "redirect:/user/cart";
//     }

//     @PostMapping("/update/{id}")
//     public String updateQuantity(@PathVariable int id, @RequestParam int quantity,
//             RedirectAttributes redirectAttributes) {
//         if (quantity > 0) {
//             cartService.updateQuantity(id, quantity);
//             redirectAttributes.addFlashAttribute("message", "Cập nhật số lượng thành công!");
//         } else {
//             cartService.removeItem(id);
//             redirectAttributes.addFlashAttribute("message", "Sản phẩm đã được xóa khỏi giỏ hàng!");
//         }
//         return "redirect:/user/cart";
//     }

//     @PostMapping("/remove/{id}")
//     public String removeItem(@PathVariable int id, RedirectAttributes redirectAttributes) {
//         cartService.removeItem(id);
//         redirectAttributes.addFlashAttribute("message", "Sản phẩm đã bị xóa!");
//         return "redirect:/user/cart";
//     }

//     @PostMapping("/clear")
//     public String clearCart(RedirectAttributes redirectAttributes) {
//         cartService.clearCart();
//         redirectAttributes.addFlashAttribute("message", "Giỏ hàng đã được làm trống!");
//         return "redirect:/user/cart";
//     }
// }
