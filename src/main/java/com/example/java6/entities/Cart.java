package com.example.java6.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
// Cart.java
@Entity
@Table(name = "Carts")
@Data
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Username", nullable = false)
    private Account account;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> cartItems;
}


