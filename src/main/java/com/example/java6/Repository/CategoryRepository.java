package com.example.java6.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.java6.Model.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {
    // Có thể thêm phương thức tìm kiếm tùy chỉnh nếu cần
}
