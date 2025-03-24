package com.example.java6.controllers.admin;

import com.example.java6.entities.Account;
import com.example.java6.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private static final String UPLOAD_DIR = "D:\\HocKy5\\Java6\\java6\\src\\main\\resources\\static\\images";

    @GetMapping
    public String listAccounts(Model model) {
        model.addAttribute("accounts", accountService.findAll());
        return "account/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("account", new Account());
        return "account/form";
    }

    @PostMapping("/save")
    public String saveAccount(@ModelAttribute Account account,
            @RequestParam("file") MultipartFile file) throws IOException {
        // Nếu có file ảnh, lưu vào thư mục và cập nhật đường dẫn
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            File saveFile = new File(UPLOAD_DIR + fileName);
            file.transferTo(saveFile);
            account.setPhoto("/images/" + fileName);
        }

        // Mã hóa mật khẩu nếu tài khoản mới hoặc có thay đổi mật khẩu
        if (account.getPassword() != null && !account.getPassword().isEmpty()) {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
        }

        accountService.save(account);
        return "redirect:/accounts";
    }

    @GetMapping("/edit/{username}")
    public String showEditForm(@PathVariable String username, Model model) {
        Optional<Account> account = accountService.findById(username);
        account.ifPresent(value -> model.addAttribute("account", value));
        return account.isPresent() ? "account/form" : "redirect:/accounts";
    }

    @GetMapping("/delete/{username}")
    public String deleteAccount(@PathVariable String username) {
        accountService.deleteById(username);
        return "redirect:/accounts";
    }
}
