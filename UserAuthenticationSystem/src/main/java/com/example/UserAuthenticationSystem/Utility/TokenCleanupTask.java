package com.example.UserAuthenticationSystem.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.UserAuthenticationSystem.Entity.User;
import com.example.UserAuthenticationSystem.Service.UserService;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class TokenCleanupTask {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Scheduled(fixedRate = 60000) // Runs every 60 seconds
    public void cleanUpExpiredTokens() {
        System.out.println("Starting token cleanup task");

        // Retrieve all users from the database
        Iterable<User> users = userService.findAllUsers();

        for (User user : users) {
            String token = user.getJwtToken();
            if (token != null) {
                try {
                    if (jwtUtil.isTokenExpired(token)) {
                        userService.logout(token);
                    }
                } catch (ExpiredJwtException e) {
                    // Token is already expired
                    userService.logout(token);
                }
            }
        }
        System.out.println("Token cleanup task completed");
    }
}
