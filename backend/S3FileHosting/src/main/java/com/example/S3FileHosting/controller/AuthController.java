package com.example.S3FileHosting.controller;


import com.example.S3FileHosting.dto.LoginDto;
import com.example.S3FileHosting.dto.UserDto;
import com.example.S3FileHosting.exception.CustomerAlreadyExistsException;
import com.example.S3FileHosting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class AuthController {

    @Autowired
    UserService userService;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {

        try {
            userService.saveUser(userDto);
        } catch (CustomerAlreadyExistsException exception) {
            return new ResponseEntity<>("Duplicate username/email.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Registration done. Check mail to verify signup.", HttpStatus.OK);
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    // handler method to handle login request
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        boolean doesUserExist = userService.checkCredentials(loginDto.getEmail(), loginDto.getPassword());
        if (doesUserExist) {
            return new ResponseEntity<>("Successful login.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Duplicate username/email.", HttpStatus.BAD_REQUEST);
        }
    }


}
