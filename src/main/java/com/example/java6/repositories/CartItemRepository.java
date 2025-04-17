package com.example.java6.repositories;

import com.example.java6.entities.CartItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// CartRepository.java
// CartItemRepository.java
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart_Id(Long cartId);
    void deleteByCart_Id(Long cartId);
}
