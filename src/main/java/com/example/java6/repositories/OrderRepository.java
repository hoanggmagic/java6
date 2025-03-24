package com.example.java6.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.java6.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
