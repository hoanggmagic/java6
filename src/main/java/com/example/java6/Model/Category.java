package com.example.java6.Model;


import lombok.Data;
import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "Categories")
public class Category implements Serializable {
    @Id
    private String id;
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}