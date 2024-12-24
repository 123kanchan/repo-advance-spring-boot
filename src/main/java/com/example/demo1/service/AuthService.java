package com.example.demo1.service;

import com.example.demo1.payload.LoginDto;
import com.example.demo1.payload.RegisterDto;

//for login  api
public interface AuthService {
   public String login(LoginDto dto);
   public String register(RegisterDto sto);
}
