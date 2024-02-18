package com.example.S3FileHosting.controller;


import com.example.S3FileHosting.dto.JwtAuthResponse;
import com.example.S3FileHosting.dto.LoginDto;
import com.example.S3FileHosting.dto.RegisterDto;
import com.example.S3FileHosting.dto.UserDto;
import com.example.S3FileHosting.service.AuthServiceImpl;
import com.example.S3FileHosting.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    AuthServiceImpl authService;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto) {
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
        String response = authService.login(loginDto);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(response);
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }


}
