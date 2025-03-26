package com.example.java6.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ServiceContactController {

    @GetMapping("/services")
    public String servicesPage(Model model) {
        model.addAttribute("title", "Dịch Vụ");
        return "user/home/services";
    }

    @GetMapping("/contact")
    public String contactPage(Model model) {
        model.addAttribute("title", "Liên Hệ");
        return "user/home/contact";
    }
}
