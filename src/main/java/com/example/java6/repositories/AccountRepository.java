package com.example.java6.repositories;

import com.example.java6.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByEmail(String email); // Tìm tài khoản theo email

    boolean existsByEmail(String email);
}
