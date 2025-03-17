package com.example.java6.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.java6.Model.Account;
import com.example.java6.Repository.AccountRepository;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;

    // Lấy danh sách tất cả tài khoản
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    // Tìm tài khoản theo username
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findById(username);
    }

    // Lưu hoặc cập nhật tài khoản
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    // Xóa tài khoản
    public void deleteByUsername(String username) {
        accountRepository.deleteById(username);
    }
}
