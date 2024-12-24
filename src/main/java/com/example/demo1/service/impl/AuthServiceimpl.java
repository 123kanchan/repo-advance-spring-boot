package com.example.demo1.service.impl;

import com.example.demo1.entity.Role;
import com.example.demo1.entity.User;
import com.example.demo1.exception.BlogAPIException;
import com.example.demo1.payload.LoginDto;
import com.example.demo1.payload.RegisterDto;
import com.example.demo1.repository.RoleRepository;
import com.example.demo1.repository.UserRepository;
import com.example.demo1.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

//for login api and then register api
@Service
public class AuthServiceimpl implements AuthService {

///for login tis is only needed
    private AuthenticationManager auth;
//for register tgese are also needed
@Autowired
UserRepository repo1;
    @Autowired
RoleRepository repo2;

PasswordEncoder encoderr;
    @Autowired
RoleRepository repo;
    @Autowired
    private JWTTokenProvider jwtTokenProvider;
    public AuthServiceimpl(AuthenticationManager auth,UserRepository repo1,RoleRepository repo,PasswordEncoder encoderr,JWTTokenProvider jwtTokenProvider) {
        this.auth = auth;
        this.encoderr=encoderr;
        this.repo1=repo1;
        this.repo=repo;
        this.jwtTokenProvider=jwtTokenProvider;
    }

    @Override
    public String login(LoginDto dto) {
        Authentication authentication= auth.authenticate(new UsernamePasswordAuthenticationToken(dto.getUserNameemail(),dto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token=jwtTokenProvider.generatetoken(authentication);
        //return "user logged in succesfully";
        return token;
    }

    @Override
    public String register(RegisterDto sto) {

        //check if username exists or not

        if(repo1.existsByUsername(sto.getUsername())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"username already exists");
        }

        //if email already exists
        if(repo1.existsByEmail(sto.getEmail())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"email already exists");
        }

        User user=new User();
        user.setName(sto.getName());
        user.setEmail(sto.getEmail());
        user.setUsername(sto.getUsername());
        //as saving in db ncrypted password
        PasswordEncoder p=new BCryptPasswordEncoder();
        user.setPassword(p.encode(sto.getPassword()));
  Set<Role> s=new HashSet<>();
  Role role=repo2.findByName("ROLE_USER").get();
  s.add(role);
  user.setRoles(s);
  repo1.save(user);
  return "user regioster succesfully";
    }
}
