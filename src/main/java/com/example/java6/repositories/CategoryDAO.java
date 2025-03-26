package com.example.java6.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.java6.entities.Category;

public interface CategoryDAO extends JpaRepository<Category, String> {
    Optional<Category> findByName(String name); // Kiểm tra danh mục theo tên
}

