package com.example.java6.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.java6.entities.OrderDetail;

public interface OrderDetailDAO extends JpaRepository<OrderDetail, Long> {
}