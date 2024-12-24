package com.example.demo1.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//register api
public class RegisterDto {
    private String name;
    private String username;
    private String email;
    private String password;
}
