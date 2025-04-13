package com.example.java6.controllers;

import com.example.java6.entities.Account;
import com.example.java6.services.AccountService;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AccountService accountService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(AccountService accountService, BCryptPasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Hiển thị trang đăng nhập
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
            @RequestParam String password,
            HttpSession session, Model model) {
        Account account = accountService.findByEmail(username);

        if (account != null && passwordEncoder.matches(password, account.getPassword())) { // Kiểm tra đúng cách
            session.setAttribute("loggedInUser", account);
            return "redirect:/home"; // Điều hướng đến trang chủ
        }

        model.addAttribute("error", "Email hoặc mật khẩu không đúng!");
        return "login"; // Trả về trang login với thông báo lỗi
    }

    @GetMapping("/sign-up")
    public String showSignUpPage(Model model) {
        model.addAttribute("account", new Account());
        return "sign-up"; // Hiển thị sign-up.html
    }

    @PostMapping("/sign-up")
    public String register(@ModelAttribute Account account) {
        account.setAdmin(false); // Mặc định là USER
        accountService.saveAccount(account);
        return "redirect:/auth/login?register_success";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Xóa session khi logout
        return "redirect:/home";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied"; // Hiển thị access-denied.html
    }

    @GetMapping("/profile")
    public String showProfilePage(HttpSession session, Model model) {
        Account loggedInUser = (Account) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("account", loggedInUser);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute Account account,
            @RequestParam("imageFile") MultipartFile imageFile,
            HttpSession session, Model model) {
        Account loggedInUser = (Account) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/auth/login";
        }

        loggedInUser.setFullname(account.getFullname());
        loggedInUser.setEmail(account.getEmail());

        if (!imageFile.isEmpty()) {
            String uploadDir = "D:\\workspace\\lab6\\src\\main\\resources\\static\\images\\";
            File uploadFolder = new File(uploadDir);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            String fileName = loggedInUser.getUsername() + "_" + imageFile.getOriginalFilename();
            File savedFile = new File(uploadDir + fileName);
            try {
                imageFile.transferTo(savedFile);
                loggedInUser.setPhoto("/images/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "Lỗi khi tải ảnh lên!");
                return "profile";
            }
        }

        if (!account.getPassword().isEmpty()) {
            loggedInUser.setPassword(passwordEncoder.encode(account.getPassword()));
        }

        accountService.saveAccount(loggedInUser);
        model.addAttribute("successMessage", "Cập nhật thông tin thành công!");
        return "profile";
    }

}