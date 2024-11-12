package com.example.ASMAN.controller;

import com.example.ASMAN.entity.Auth;
import com.example.ASMAN.entity.AuthRequest;
import com.example.ASMAN.entity.Person;
import com.example.ASMAN.service.AuthService;
import com.example.ASMAN.service.JwtService;
import com.example.ASMAN.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody Person person) {
        String response = "";
        try{
            response = userInfoService.addUser(person);
        }catch (Exception e){
            response = e.getMessage();
        }
        return response;
    }

    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(authRequest.getUsername());
            } else {
                throw new UsernameNotFoundException("Invalid user request!");
            }
        }catch (Exception e){
           System.out.println(e.getCause());
        }
        return null;
    }
}
