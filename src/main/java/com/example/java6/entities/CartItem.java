package com.example.java6.entities;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Data;
// CartItem.java
@Entity
@Table(name = "CartItems")
@Data
public class CartItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "productid", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cartid", nullable = false)
    private Cart cart;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double price;
}
