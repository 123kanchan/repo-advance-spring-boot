package com.example.demo1.service.impl;

import com.example.demo1.entity.User;
import com.example.demo1.exception.ResourceNotfoundException;
import com.example.demo1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
//this class made for database authentication
public class CustomerUserDetailsService implements UserDetailsService {
@Autowired
    private UserRepository repo;
    @Override
    public UserDetails loadUserByUsername(String usernameOremail) throws UsernameNotFoundException {
        User user =repo.findByUsernameOrEmail(usernameOremail,usernameOremail).orElseThrow(()->new UsernameNotFoundException("user not foiund with usernme or email"));
        //conevreting all roles for that particular user into granted authority
        Set<GrantedAuthority> authorities=user.getRoles().
                stream().map((role)->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(user.getEmail()
        ,user.getPassword(),authorities) ;
    }
}
