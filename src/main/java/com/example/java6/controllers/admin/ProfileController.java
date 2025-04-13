package com.example.java6.controllers.admin;

import com.example.java6.entities.Account;
import com.example.java6.services.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/edit")
    public String showProfile(Model model) {
        Account account = accountService.getCurrentUser();
        if (account == null) {
            return "redirect:/login";
        }
        model.addAttribute("account", account);
        return "user/home/edit";
    }

    @PostMapping("/update")
    public String updateProfile(
            @ModelAttribute("account") Account updatedAccount,
            @RequestParam("newPassword") String newPassword,
            Model model) {

        Account account = accountService.getCurrentUser();
        if (account == null) {
            return "redirect:/login";
        }

        // Chỉ cập nhật ảnh và mật khẩu
        account.setPhoto(updatedAccount.getPhoto());

        if (!newPassword.isBlank()) {
            account.setPassword(passwordEncoder.encode(newPassword));
        }

        try {
            accountService.save(account);
            model.addAttribute("account", account);
            model.addAttribute("successMessage", "Cập nhật thành công!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Đã xảy ra lỗi khi cập nhật!");
        }

        return "admin/home/edit";
    }
}
