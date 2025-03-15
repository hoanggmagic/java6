package com.example.java6.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.java6.Model.OrderDetail;
import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    // Tìm danh sách chi tiết đơn hàng theo ID đơn hàng
    List<OrderDetail> findByOrderId(Long orderId);
}
