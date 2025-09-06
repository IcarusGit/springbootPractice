package com.registration.practice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.registration.practice.dto.Login.LoginRequestDto;
import com.registration.practice.dto.Login.LoginResponseDto;
import com.registration.practice.model.Users;
import com.registration.practice.security.JwtUtil;
import com.registration.practice.service.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication operations")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    // 1. register endpoint
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody Users user){
        String registerResult = authService.registerUser(user);

        return ResponseEntity.ok(registerResult);
    }

    // 2. login endpoint
    // kaya postmapping kase sa body mo ilalagay yung pw at username w/c is safer
    // kapag kasi get bale sa params or pathvariable mo ilalagay pw edi makikita ng lahat
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto user){
        Users loggingInUser = authService.loginUser(user.getUsername(), user.getPassword());

        if(loggingInUser != null){
            String token = JwtUtil.generateToken(loggingInUser.getUsername());
            LoginResponseDto loginResponse = new LoginResponseDto(
                "Login Successful", 
                loggingInUser.getUsername(), 
                loggingInUser.getRole(), 
                token);
            
            return ResponseEntity.ok(loginResponse);
        } else {
            return ResponseEntity.status(401).body(new LoginResponseDto(
                "Invalid credentials.", null, null, null));
        }
    }
}
