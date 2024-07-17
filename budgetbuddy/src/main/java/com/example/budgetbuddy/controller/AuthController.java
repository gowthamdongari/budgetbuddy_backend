package com.example.budgetbuddy.controller;

import com.example.budgetbuddy.dto.AuthResponse;
import com.example.budgetbuddy.dto.LoginRequest;
import com.example.budgetbuddy.dto.RegisterRequest;
import com.example.budgetbuddy.services.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

   private final  LoginService loginService;

    public AuthController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest){
          boolean isRegisterSuccess =loginService.registration(registerRequest);
          if(isRegisterSuccess){
               return ResponseEntity.ok("User Registered successfully!");
          }
          return ResponseEntity.badRequest().body("Email is already in use!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginController(@RequestBody LoginRequest loginRequest){
        AuthResponse authResponse = loginService.login(loginRequest);
        if(authResponse!=null){
            return ResponseEntity.ok(authResponse);
        }
        return  ResponseEntity.badRequest().body("Invalid credentials");
    }


}
