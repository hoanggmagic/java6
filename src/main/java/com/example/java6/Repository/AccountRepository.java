package com.example.java6.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.java6.Model.Account;

public interface AccountRepository extends JpaRepository<Account, String> {
    // Có thể thêm các phương thức tìm kiếm tùy chỉnh nếu cần
}

