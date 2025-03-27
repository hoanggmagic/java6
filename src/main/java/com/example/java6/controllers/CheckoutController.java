package com.example.java6.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

@Controller
public class CheckoutController {

    @RequestMapping("/test")
    public String home(Model model, Authentication authentication) {
        return "test";
    }
}
