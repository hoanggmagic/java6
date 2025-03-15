package com.example.java6.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String home() {
        return "admin/home/index"; // Trả về tên file index.html trong thư mục templates
    }
}
