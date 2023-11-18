package com.example.oauth2.repository;


import com.example.oauth2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByRole(String role);
    Optional<User> findById(Integer id);
    Boolean existsByEmailAndRole(String email, String role);
}
