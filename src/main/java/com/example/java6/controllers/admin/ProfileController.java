package com.example.java6.controllers.admin;

import com.example.java6.entities.Account;
import com.example.java6.services.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

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
            @RequestParam("imageFile") MultipartFile imageFile, // THÊM VÀO
            Model model) {

        Account account = accountService.getCurrentUser();
        if (account == null) {
            return "redirect:/login";
        }

        account.setFullname(updatedAccount.getFullname());
        account.setEmail(updatedAccount.getEmail());

        // XỬ LÝ ẢNH
        if (!imageFile.isEmpty()) {
            try {
                String uploadDir = "C:\\Users\\Admin\\Desktop\\LapTrinhJava6\\java6\\java6\\src\\main\\resources\\static\\images\\";
                ;
                File dir = new File(uploadDir);
                if (!dir.exists())
                    dir.mkdirs();

                String fileName = imageFile.getOriginalFilename();
                File saveFile = new File(uploadDir + fileName);
                imageFile.transferTo(saveFile);

                account.setPhoto(fileName); // chỉ lưu tên file thôi
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("errorMessage", "Lỗi khi tải ảnh!");
                return "user/home/edit";
            }
        }

        // Nếu có mật khẩu mới thì cập nhật
        if (!newPassword.isBlank()) {
            account.setPassword(passwordEncoder.encode(newPassword));
        }

        try {
            accountService.save(account);
            model.addAttribute("account", account);
            model.addAttribute("successMessage", "Cập nhật thành công!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Lỗi khi cập nhật!");
        }

        return "user/home/edit";
    }
}
// @PostMapping("/update")
// public String updateProfile(
// @ModelAttribute("account") Account updatedAccount,
// @RequestParam("newPassword") String newPassword,
// Model model) {

// Account account = accountService.getCurrentUser();
// if (account == null) {
// return "redirect:/login";
// }

// account.setFullname(updatedAccount.getFullname());
// account.setEmail(updatedAccount.getEmail());
// account.setPhoto(updatedAccount.getPhoto());

// if (!newPassword.isBlank()) {
// account.setPassword(passwordEncoder.encode(newPassword));
// }

// try {
// accountService.save(account);
// model.addAttribute("account", account);
// model.addAttribute("successMessage", "Thông tin đã được cập nhật thành
// công!");
// } catch (Exception e) {
// model.addAttribute("errorMessage", "Đã xảy ra lỗi khi cập nhật thông tin!");
// }