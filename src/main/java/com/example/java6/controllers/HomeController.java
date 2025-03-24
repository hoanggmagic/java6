package com.example.java6.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails userDetails) {
                model.addAttribute("username", userDetails.getUsername()); // Gửi username ra giao diện

                for (GrantedAuthority authority : userDetails.getAuthorities()) {
                    if (authority.getAuthority().equals("ROLE_ADMIN")) {
                        return "admin/home/index.html";
                    }
                }
            }
        }
        return "user/home/indexUser.html";
    }
}
