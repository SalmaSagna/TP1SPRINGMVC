package com.hackathon.controller;

import com.hackathon.dto.RegisterRequest;
import com.hackathon.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AuthService authService;

    public AdminController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@RequestBody RegisterRequest request) {
        authService.registerWithRole(request);
        return ResponseEntity.ok().build();
    }
}