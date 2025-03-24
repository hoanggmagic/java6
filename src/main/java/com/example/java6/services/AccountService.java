package com.example.java6.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.java6.entities.Account;
import com.example.java6.repositories.AccountRepository;

@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AccountService(AccountRepository accountRepository, BCryptPasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email không tồn tại"));

        return User.builder()
                .username(account.getEmail()) // Sử dụng email làm username
                .password(account.getPassword()) // Mật khẩu đã mã hóa
                .authorities(account.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER") // Fix role format
                .build();
    }

    public void saveAccount(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword())); // Mã hóa mật khẩu
        accountRepository.save(account);
    }

    public synchronized boolean register(Account account) {
        if (accountRepository.existsByEmail(account.getEmail())) { // Kiểm tra nhanh hơn
            return false; // Email đã tồn tại
        }
        account.setPassword(passwordEncoder.encode(account.getPassword())); // Mã hóa mật khẩu
        account.setAdmin(false); // Mặc định là USER
        account.setActivated(true); // Kích hoạt tài khoản
        accountRepository.save(account);
        return true;
    }

    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email).orElse(null); // Trả về null nếu không tìm thấy
    }

    public Account getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            return findByEmail(email); // Dùng email thay vì username
        }
        return null;
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public Optional<Account> findByUsername(String username) {
        return accountRepository.findById(username);
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    public void deleteByUsername(String username) {
        accountRepository.deleteById(username);
    }

    public Optional<Account> findById(String username) {
        return accountRepository.findById(username);
    }

    public void deleteById(String username) {
        accountRepository.deleteById(username);
    }
}
