package com.example.ASMAN.repository;

import com.example.ASMAN.entity.Auth;
import com.example.ASMAN.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Integer> {
    Optional<Auth> findByEmail(String email);
    Optional<Auth> findByUsername(String username);
}
