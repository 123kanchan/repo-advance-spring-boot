package com.example.demo1.utils;


//for database athentication as pass in db are string so better give a randome mame

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Passwordencoder {
    public static void main(String[] args) {
        PasswordEncoder p=new BCryptPasswordEncoder();
        System.out.println(p.encode("admin"));
        System.out.println(p.encode("kanchan"));

    }
}
