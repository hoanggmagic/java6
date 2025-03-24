package com.example.java6.services;

import com.example.java6.entities.*;
import com.example.java6.repositories.OrderRepository;
import com.example.java6.repositories.AccountRepository;
import com.example.java6.repositories.OrderDetailRepository;
import com.example.java6.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    // @Transactional
    // public Order createOrder(String username, String address, List<OrderDetail>
    // orderDetails) {
    // // Kiểm tra tài khoản
    // Account account = accountRepository.findById(username)
    // .orElseThrow(() -> new RuntimeException("Không tìm thấy tài khoản!"));

    // // Tạo đơn hàng
    // Order order = new Order();
    // order.setAccount(account);
    // order.setAddress(address);
    // order.setCreateDate(new Date());

    // order = orderRepository.save(order);

    // // Lưu chi tiết đơn hàng
    // for (OrderDetail detail : orderDetails) {
    // Product product = productRepository.findById(detail.getProduct().getId())
    // .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại!"));

    // detail.setOrder(order);
    // detail.setProduct(product);
    // orderDetailRepository.save(detail);
    // }

    // return order;
    // }

    @Transactional
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }
}
