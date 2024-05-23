package com.example.UserAuthenticationSystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/reset-password-request")
    public String showResetPasswordRequestPage() {
        return "reset-Password-Request";
    }

    @GetMapping("/showPasswordResetForm")
    public String showPasswordResetForm(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "password-reset";
    }
    
    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }
}
