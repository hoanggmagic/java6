// package com.example.java6.services.impl;

// import com.example.java6.services.*;
// import com.example.java6.entities.Account;
// import com.example.java6.entities.Cart;
// import com.example.java6.entities.CartItem;
// import com.example.java6.entities.OrderDetail;
// import com.example.java6.entities.Product;
// import com.example.java6.repositories.*;
// import com.example.java6.services.CartService;

// import jakarta.transaction.Transactional;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// @Transactional
// public class CartServiceImpl implements CartService {

// private final CartRepository cartRepository;
// private final CartItemRepository cartItemRepository;
// private final AccountRepository accountRepository;
// private final ProductRepository productRepository;

// public CartServiceImpl(CartRepository cartRepository,
// CartItemRepository cartItemRepository,
// AccountRepository accountRepository,
// ProductRepository productRepository) {
// this.cartRepository = cartRepository;
// this.cartItemRepository = cartItemRepository;
// this.accountRepository = accountRepository;
// this.productRepository = productRepository;
// }

// // Lấy hoặc tạo mới giỏ hàng
// private Cart getOrCreateCart(String username) {
// Account account = accountRepository.findByUsername(username)
// .orElseThrow(() -> new RuntimeException("Account not found"));
// return cartRepository.findByAccount(account)
// .orElseGet(() -> {
// Cart cart = new Cart();
// cart.setAccount(account);
// return cartRepository.save(cart);
// });
// }

// @Override
// public void addToCart(String username, int productId) {
// // Lấy giỏ hàng của người dùng
// Cart cart = getOrCreateCart(username);

// // Lấy sản phẩm theo ID
// Product product = productRepository.findById(productId)
// .orElseThrow(() -> new RuntimeException("Product not found"));

// // Kiểm tra sản phẩm đã có trong giỏ chưa
// CartItem item = cartItemRepository.findByCartAndProduct(cart, product)
// .orElseGet(() -> {
// // Nếu chưa có thì tạo mới CartItem
// CartItem newItem = new CartItem();
// newItem.setCart(cart);
// newItem.setProduct(product);
// newItem.setQuantity(0); // Bắt đầu với số lượng = 0
// newItem.setPrice(product.getPrice());
// return newItem;
// });

// // Cập nhật số lượng sản phẩm trong giỏ
// item.setQuantity(item.getQuantity() + 1); // Tăng số lượng lên 1
// cartItemRepository.save(item); // Lưu lại CartItem
// }

// // Các phương thức khác như updateQuantity, removeItem, clearCart...
// @Override
// public void updateQuantity(String username, int productId, int quantity) {
// Cart cart = getOrCreateCart(username);
// Product product = productRepository.findById(productId).orElseThrow();
// CartItem item = cartItemRepository.findByCartAndProduct(cart,
// product).orElseThrow();
// item.setQuantity(quantity);
// cartItemRepository.save(item);
// }

// @Override
// public void removeItem(String username, int productId) {
// Cart cart = getOrCreateCart(username);
// Product product = productRepository.findById(productId).orElseThrow();
// CartItem item = cartItemRepository.findByCartAndProduct(cart,
// product).orElseThrow();
// cartItemRepository.delete(item);
// }

// @Override
// public void clearCart(String username) {
// Cart cart = getOrCreateCart(username);
// List<CartItem> items = cartItemRepository.findByCart(cart);
// cartItemRepository.deleteAll(items);
// }

// @Override
// public List<CartItem> getCartItems(String username) {
// Cart cart = getOrCreateCart(username);
// return cartItemRepository.findByCart(cart);
// }

// @Override
// public double getTotal(String username) {
// return getCartItems(username).stream()
// .mapToDouble(item -> item.getPrice() * item.getQuantity())
// .sum();
// }
// }
