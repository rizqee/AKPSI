package com.example.ASMAN.service;

import com.example.ASMAN.entity.Auth;
import com.example.ASMAN.entity.Person;
import com.example.ASMAN.repository.AuthRepository;
import com.example.ASMAN.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.ASMAN.entity.UserInfoDetails;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AuthRepository authRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> userDetail = personRepository.findByEmail(username);
        if(userDetail.isEmpty()){
            userDetail = personRepository.findByUsername(username);
        }
        Optional<Auth> auth = authRepository.findByUsername(username);
        if(auth.isEmpty()){
            auth = authRepository.findByEmail(username);
        }
        // Converting userDetail and Auth to UserInfoDetails
        if(userDetail.isPresent() && auth.isPresent()){
            return new UserInfoDetails(auth.get(),userDetail.get());
        }else{
            throw new UsernameNotFoundException("User not found: " + username);
        }
    }

    @Transactional
    public String addUser(Person person) {
        // Encode password before saving the user
        personRepository.save(person);
        Auth auth = Auth.builder()
                .email(person.getEmail())
                .username(person.getUsername())
                .password(encoder.encode("123456"))
                .build();
        authRepository.save(auth);
        return "user created password is set to 123456 please update the password";
    }
}
