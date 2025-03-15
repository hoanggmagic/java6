package com.example.java6.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.java6.Model.Order;
import com.example.java6.Repository.OrderRepository;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;

    // Lấy tất cả đơn hàng
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    // Tìm đơn hàng theo ID
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    // Tìm đơn hàng theo username của khách hàng
    public List<Order> findByUsername(String username) {
        return orderRepository.findByAccountUsername(username);
    }

    // Lưu hoặc cập nhật đơn hàng
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    // Xóa đơn hàng theo ID
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}
