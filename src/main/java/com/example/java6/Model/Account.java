package com.example.java6.Model;


import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Accounts")
public class Account implements Serializable {
    @Id
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String photo;
    private boolean activated;
    private boolean admin;

    @OneToMany(mappedBy = "account")
    private List<Order> orders;

    public void setPassword(String password) {
        this.password = password;
    }
}