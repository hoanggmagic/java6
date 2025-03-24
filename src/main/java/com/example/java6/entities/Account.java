package com.example.java6.entities;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@Entity
@Table(name = "Accounts")
public class Account implements Serializable {
    @Id
    @Column(length = 50, nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String fullname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 255)
    private String photo;

    private boolean activated;
    private boolean admin;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Order> orders;
}
