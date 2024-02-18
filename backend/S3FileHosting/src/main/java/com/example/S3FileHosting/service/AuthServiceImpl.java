package com.example.S3FileHosting.service;

import com.example.S3FileHosting.dto.LoginDto;
import com.example.S3FileHosting.dto.RegisterDto;
import com.example.S3FileHosting.entity.Role;
import com.example.S3FileHosting.entity.User;
import com.example.S3FileHosting.exception.CustomerAlreadyExistsException;
import com.example.S3FileHosting.repo.RoleRepo;
import com.example.S3FileHosting.repo.UserRepo;
import com.example.S3FileHosting.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public String register(RegisterDto registerDto) {

        checkIfUserAlreadyExists(registerDto);

        List<Role> roles = new ArrayList<>();
        roles.add(roleRepo.findByName("ROLE_USER"));

        User user = new User(registerDto.getUsername(),
                registerDto.getEmail(),
                registerDto.getName(),
                passwordEncoder.encode(registerDto.getPassword()),
                roles);

        userRepo.save(user);

        return "User saved successfully!";
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authenticate = null;
        try {
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmailOrUsername(),
                    loginDto.getPassword()));
        } catch (Throwable e) {
            throw new CustomerAlreadyExistsException(HttpStatus.BAD_REQUEST,
                    String.format("Incorrect credentials", loginDto.getEmailOrUsername()));
        }
        System.out.println("Authenticate object found.");
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtTokenProvider.generateToken(authenticate);
        return token;
    }

    private void checkIfUserAlreadyExists(RegisterDto registerDto) {
        String username = registerDto.getUsername();
        if (userRepo.existsByUsername(username)) {
            throw new CustomerAlreadyExistsException(HttpStatus.BAD_REQUEST,
                    String.format("Username %s exists in the database", username));
        }
        String email = registerDto.getEmail();
        if (userRepo.existsByEmail(email)) {
            throw new CustomerAlreadyExistsException(HttpStatus.BAD_REQUEST,
                    String.format("Email %s exists in the database", email));
        }

    }
}
