package com.example.SmartShop.controller;

import com.example.SmartShop.dto.LoginDto;
import com.example.SmartShop.model.entitie.User;
import com.example.SmartShop.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto, HttpServletRequest request) {

        return userService.authenticate(dto.getUsername(), dto.getPassword())
                .map(user -> {
                    request.getSession().setAttribute("user", user);
                    return ResponseEntity.ok("Logged in successfully");
                })
                .orElseGet(() -> ResponseEntity.status(401).body("Invalid credentials"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return ResponseEntity.ok("Logged out successfully");
    }
}
