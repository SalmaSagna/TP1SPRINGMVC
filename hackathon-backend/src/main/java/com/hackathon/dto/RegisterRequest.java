package com.hackathon.dto;

import com.hackathon.entities.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private Role role;
}