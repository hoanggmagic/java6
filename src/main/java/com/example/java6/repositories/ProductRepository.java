package com.example.java6.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.java6.entities.Product;
import com.example.java6.entities.Category; // nếu cần cho group theo category

public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Trả về 5 sản phẩm mới nhất theo ngày tạo
    List<Product> findTop5ByOrderByCreateDateDesc();

    // DSL: Tìm sản phẩm theo khoảng giá
    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    // DSL: Tìm sản phẩm theo từ khóa tên (có phân trang)
    Page<Product> findAllByNameLike(String keywords, Pageable pageable);

    // Báo cáo tổng hợp sản phẩm theo category
    @Query("SELECT o.category AS group, sum(o.price) AS sum, count(o) AS count "
            + "FROM Product o "
            + "GROUP BY o.category "
            + "ORDER BY sum(o.price) DESC")
    List<Object[]> getInventoryByCategory();

}

// import java.util.List;

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;

// import com.example.java6.entities.Product;

// public interface ProductRepository extends JpaRepository<Product, Integer> {

// List<Product> findTop5ByOrderByCreateDateDesc();
// // bài1 lab7 phương thức truy vấn @query(jpql)
// // @Query("FROM Product o WHERE o.price BETWEEN ?1 AND ?2")
// // List<Product> findByPrice(double minPrice, double maxPrice);

// // bài4 lab7 sd DSL
// List<Product> findByPriceBetween(double minPrice, double maxPrice);

// // bai2 lab7
// // @Query("FROM Product o WHERE o.name LIKE ?1")
// // Page<Product> findByKeywords(String keywords, Pageable pageable);

// // bai5 lab7
// Page<Product> findAllByNameLike(String keywords, Pageable pageable);

// @Query("SELECT o.category AS group, sum(o.price) AS sum, count(o) AS count "
// + " FROM Product o "
// + " GROUP BY o.category"
// + " ORDER BY sum(o.price) DESC")
// List<Report> getInventoryByCategory();

// }
