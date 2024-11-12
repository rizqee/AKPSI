package com.example.ASMAN.repository;

import com.example.ASMAN.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByEmail(String email);
    Optional<Person> findByUsername(String username);
}
