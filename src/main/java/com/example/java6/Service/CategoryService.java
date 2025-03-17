package com.example.java6.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.java6.Model.Category;
import com.example.java6.Repository.CategoryRepository;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    // Lấy danh sách tất cả danh mục
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    // Tìm danh mục theo ID
    public Optional<Category> findById(String id) {
        return categoryRepository.findById(id);
    }

    // Lưu hoặc cập nhật danh mục
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    // Xóa danh mục theo ID
    public void deleteById(String id) {
        categoryRepository.deleteById(id);
    }
}
