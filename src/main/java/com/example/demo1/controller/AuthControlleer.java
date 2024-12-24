package com.example.demo1.controller;

import com.example.demo1.payload.JWTAuthResponse;
import com.example.demo1.payload.LoginDto;
import com.example.demo1.payload.RegisterDto;
import com.example.demo1.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//for login api
@RestController
@RequestMapping("/api/auth/v1")
public class AuthControlleer {
    AuthService service;

    public AuthControlleer(AuthService service) {
        this.service = service;
    }

    //for login api
    @PostMapping(value= {"/login","/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto logindto){
        String response=service.login(logindto);
        JWTAuthResponse jwtAuthResponse =new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(response);
        return ResponseEntity.ok(jwtAuthResponse);
    }


    //for register api
    @PostMapping(value= {"/register","/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto dto) {
        String response=service.register(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
