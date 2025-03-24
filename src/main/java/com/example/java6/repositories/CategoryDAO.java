package com.example.java6.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.java6.entities.Category;

public interface CategoryDAO extends JpaRepository<Category, String> {
}
