package com.example.UserAuthenticationSystem.Controller;

import com.example.UserAuthenticationSystem.Entity.User;
import com.example.UserAuthenticationSystem.Service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }
    
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }
        
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String email, @RequestParam String password) {
        String token = userService.loginUser(email, password);
        if (token != null) {
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    
    
    @PostMapping("/reset-password-request")
    public ResponseEntity<String> requestPasswordReset(@RequestParam String email) {
        boolean resetRequested = userService.requestPasswordReset(email);
        if (resetRequested) {
            return ResponseEntity.ok("Password reset email sent successfully");
        } else {
            return ResponseEntity.badRequest().body("User not found or password reset already requested");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam("token") String token, 
                                                @RequestParam("newPassword") String newPassword) {
        boolean resetSuccess = userService.resetPassword(token, newPassword);
        if (resetSuccess) {
            return ResponseEntity.ok("Password reset successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }
    }
    
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            userService.logout(token);
            return ResponseEntity.ok("Logout successful");
        } else {
            return ResponseEntity.badRequest().body("Invalid Authorization header");
        }
    }
    

}
