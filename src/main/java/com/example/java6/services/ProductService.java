package com.example.java6.services;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.stereotype.Service;

// import com.example.java6.entities.Product;
// import com.example.java6.repositories.ProductRepository;

// @Service
// public class ProductService {

//     @Autowired
//     private ProductRepository productRepository;

//     // Trả về danh sách tất cả sản phẩm (không phân trang)
//     public List<Product> findAll() {
//         return productRepository.findAll();
//     }

//     // Trả về danh sách sản phẩm phân trang
//     public Page<Product> findAll(Pageable pageable) {
//         return productRepository.findAll(pageable);
//     }

//     // Tìm sản phẩm theo ID
//     public Product findById(Integer id) {
//         return productRepository.findById(id).orElse(null);
//     }

//     // Lấy 5 sản phẩm mới nhất theo ngày tạo
//     public List<Product> getNewProducts() {
//         return productRepository.findTop5ByOrderByCreateDateDesc();
//     }

//     // Thêm hoặc cập nhật sản phẩm
//     public void save(Product product) {
//         productRepository.save(product);
//     }

//     // Xóa sản phẩm theo ID
//     public void deleteById(Integer id) {
//         productRepository.deleteById(id);
//     }
// }

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.java6.entities.Product;
import com.example.java6.repositories.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    // Lấy sản phẩm theo ID
    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElse(null); // Nếu không tìm thấy, trả về null
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(int id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null); // Trả về null nếu không tìm thấy sản phẩm
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product findById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }

    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public List<Product> getNewProducts() {
        return productRepository.findTop5ByOrderByCreateDateDesc();
    }

}
