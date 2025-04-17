package com.example.java6.repositories;

import com.example.java6.entities.Cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// CartRepository.java
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByAccount_Username(String username);
}

