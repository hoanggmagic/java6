package com.example.java6.controllers;

import com.example.java6.entities.Account;
import com.example.java6.services.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AccountService accountService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(AccountService accountService, BCryptPasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }

    // GET: Hiển thị form login
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // POST: Xử lý đăng nhập
    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                                @RequestParam String password,
                                HttpSession session, Model model) {
        Account account = accountService.findByEmail(username);
        if (account != null && passwordEncoder.matches(password, account.getPassword())) {
            session.setAttribute("loggedInUser", account);
            return "redirect:/products/get";  // Chuyển hướng đến trang sản phẩm sau khi đăng nhập thành công
        }

        model.addAttribute("error", "Email hoặc mật khẩu không đúng!");
        return "login";
    }

    // GET: Hiển thị form đăng ký
    @GetMapping("/sign-up")
    public String showSignUpPage(Model model) {
        model.addAttribute("account", new Account());
        return "sign-up";
    }

    // POST: Xử lý đăng ký
    @PostMapping("/sign-up")
    public String register(@ModelAttribute Account account) {
        account.setAdmin(false); // Tài khoản thường
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountService.saveAccount(account);
        return "redirect:/auth/login?register_success";
    }

    // GET: Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Xoá session hiện tại
        return "redirect:/home";
    }

    // GET: Khi truy cập bị từ chối
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

    // GET: Hiển thị trang thông tin cá nhân
    @GetMapping("/profile")
    public String showProfilePage(HttpSession session, Model model) {
        Account user = (Account) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/auth/login";

        model.addAttribute("account", user);
        return "profile";
    }

    // POST: Cập nhật thông tin cá nhân
    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute Account account,
                                @RequestParam("imageFile") MultipartFile imageFile,
                                HttpSession session, Model model) {
        Account user = (Account) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/auth/login";

        user.setFullname(account.getFullname());
        user.setEmail(account.getEmail());

        // Upload hình đại diện nếu có
        if (!imageFile.isEmpty()) {
            String uploadDir = "D:\\workspace\\lab6\\src\\main\\resources\\static\\images\\";
            File folder = new File(uploadDir);
            if (!folder.exists()) folder.mkdirs();

            String fileName = user.getUsername() + "_" + imageFile.getOriginalFilename();
            File file = new File(uploadDir + fileName);

            try {
                imageFile.transferTo(file);
                user.setPhoto("/images/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "Lỗi khi tải ảnh lên!");
                return "profile";
            }
        }

        // Nếu có thay đổi mật khẩu
        if (!account.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(account.getPassword()));
        }

        accountService.saveAccount(user);
        model.addAttribute("successMessage", "Cập nhật thông tin thành công!");
        return "profile";
    }
}
