package com.example.java6.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.java6.Model.Product;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Tìm sản phẩm theo danh mục
    List<Product> findByCategoryId(String categoryId);

    // Tìm sản phẩm theo trạng thái có sẵn
    List<Product> findByAvailableTrue();
}
