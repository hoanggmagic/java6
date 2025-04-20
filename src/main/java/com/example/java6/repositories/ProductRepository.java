package com.example.java6.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.java6.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Tìm theo khoảng giá
    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    // Tìm kiếm theo từ khóa (phân trang)
    Page<Product> findAllByNameLike(String keywords, Pageable pageable);

    // Top sản phẩm mới
    List<Product> findTop5ByOrderByCreateDateDesc();

    // Flash Sale
    @Query("SELECT p FROM Product p WHERE p.originalPrice > p.price ORDER BY (p.originalPrice - p.price) DESC")
    List<Product> findFlashSaleProducts();

    // Top sản phẩm bán chạy
    @Query("SELECT od.product FROM OrderDetail od GROUP BY od.product ORDER BY SUM(od.quantity) DESC")
    List<Product> findTopSellingProducts(Pageable pageable);
}
