package com.example.java6.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.java6.Model.Order;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Tìm danh sách đơn hàng theo username
    List<Order> findByAccountUsername(String username);
}
