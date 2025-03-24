package com.example.java6.entities;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@Entity
@Table(name = "Categories")
public class Category implements Serializable {
    @Id
    @Column(length = 50, nullable = false, unique = true)
    private String id;

    @Column(length = 100, nullable = false, unique = true) // Tên danh mục không được trùng
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore // Tránh lỗi đệ quy vô hạn khi trả về JSON
    private List<Product> products;
}
