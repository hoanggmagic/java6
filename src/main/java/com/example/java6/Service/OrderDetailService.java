package com.example.java6.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.java6.Model.OrderDetail;
import com.example.java6.Repository.OrderDetailRepository;

@Service
public class OrderDetailService {
    
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    // Lấy tất cả chi tiết đơn hàng
    public List<OrderDetail> findAll() {
        return orderDetailRepository.findAll();
    }

    // Tìm chi tiết đơn hàng theo ID
    public Optional<OrderDetail> findById(Long id) {
        return orderDetailRepository.findById(id);
    }

    // Tìm danh sách chi tiết đơn hàng theo ID đơn hàng
    public List<OrderDetail> findByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    // Lưu hoặc cập nhật chi tiết đơn hàng
    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    // Xóa chi tiết đơn hàng theo ID
    public void deleteById(Long id) {
        orderDetailRepository.deleteById(id);
    }
}
