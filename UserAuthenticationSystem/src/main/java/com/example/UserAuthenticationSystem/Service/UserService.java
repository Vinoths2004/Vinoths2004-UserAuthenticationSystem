package com.example.UserAuthenticationSystem.Service;

import com.example.UserAuthenticationSystem.Entity.User;
import com.example.UserAuthenticationSystem.Repository.UserRepository;
import com.example.UserAuthenticationSystem.Utility.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JavaMailSender javaMailSender;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    public ResponseEntity<String> registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
    
    
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    
    public String loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // Check if existing token is valid and not expired
            if (user.getJwtToken() != null && !jwtUtil.isTokenExpired(user.getJwtToken())) {
                return user.getJwtToken();
            }
            // Generate a new token if no valid token exists
            String token = jwtUtil.generateToken(user.getEmail());
            user.setJwtToken(token);
            user.setTokenExpiration(jwtUtil.extractExpiration(token));
            userRepository.save(user);
            return token;
        }
        return null;
    }

    public boolean requestPasswordReset(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            String resetToken = generateResetToken();
            user.setResetToken(resetToken);
            userRepository.save(user);
            String resetLink = "http://localhost:8089/showPasswordResetForm?token=" + resetToken;
            sendPasswordResetEmail(user.getEmail(), resetLink);
            return true;
        }
        return false;
    }

    public boolean resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token);
        if (user != null) {
            String encryptedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encryptedPassword);
            user.setResetToken(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    private String generateResetToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[16];
        secureRandom.nextBytes(token);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(token);
    }

    private void sendPasswordResetEmail(String to, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password Reset");
        message.setText("Dear User,\n\nYou have requested a password reset. Please click on the following link to reset your password:\n\n"
                + resetLink + "\n\nRegards,\nYour Application Team");
        javaMailSender.send(message);
    }

    public void logout(String token) {
        Optional<User> optionalUser = userRepository.findByJwtToken(token);
        System.out.println(optionalUser);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setJwtToken(null);
            user.setTokenExpiration(null);
            userRepository.save(user);
        }
    }

}
