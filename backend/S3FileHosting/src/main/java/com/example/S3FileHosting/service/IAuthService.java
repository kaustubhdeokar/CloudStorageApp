package com.example.S3FileHosting.service;

import com.example.S3FileHosting.dto.LoginDto;
import com.example.S3FileHosting.dto.RegisterDto;

public interface IAuthService {
    String register(RegisterDto registerDto);

    String login(LoginDto loginDto);
}
