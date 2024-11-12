package com.example.ASMAN.service;

import com.example.ASMAN.entity.Auth;
import com.example.ASMAN.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private AuthRepository repository;
    public String addUser(Auth auth){
        auth.setPassword(encoder.encode(auth.getPassword()));
        repository.save(auth);
        return "user created password is set to 123456 please update the password";
    }
}
