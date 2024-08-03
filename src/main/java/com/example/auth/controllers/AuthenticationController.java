package com.example.auth.controllers;

import com.example.auth.dtos.LoginResponse;
import com.example.auth.dtos.LoginUserDto;
import com.example.auth.dtos.RegisterUserDto;
import com.example.auth.entities.User;
import com.example.auth.services.AuthenticationService;
import com.example.auth.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto request) {
        User signup = authenticationService.signup(request);

        return ResponseEntity.ok(signup);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto request) {
        User login = authenticationService.login(request);
        System.out.println(login);
        String jwtToken = jwtService.generateToken(login);

        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
