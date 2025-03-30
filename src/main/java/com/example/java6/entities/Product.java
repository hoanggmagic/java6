package com.example.java6.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Products")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(length = 255)
    private String image;

    @Column(nullable = false)
    private Double originalPrice;

    @Column(nullable = false)
    private Double price;

    @Temporal(TemporalType.DATE)
    @Column(name = "createdate", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate = new Date();

    private Boolean available;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoryid", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderDetail> orderDetails;

    public int getDiscountPercentage() {
        if (originalPrice != null && price != null && originalPrice > price && originalPrice > 0) {
            return (int) Math.round(((originalPrice - price) / originalPrice) * 100);
        }
        return 0;
    }

}