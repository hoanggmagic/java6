package com.example.java6.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.java6.Model.Product;
import com.example.java6.Repository.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    // Lấy tất cả sản phẩm
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // Tìm sản phẩm theo ID
    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    // Tìm sản phẩm theo danh mục
    public List<Product> findByCategoryId(String categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    // Lấy danh sách sản phẩm có sẵn
    public List<Product> findAvailableProducts() {
        return productRepository.findByAvailableTrue();
    }

    // Lưu hoặc cập nhật sản phẩm
    public Product save(Product product) {
        return productRepository.save(product);
    }

    // Xóa sản phẩm theo ID
    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }
}
