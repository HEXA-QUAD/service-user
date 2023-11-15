package com.example.oauth2.controller;

import com.example.oauth2.model.User;
import com.example.oauth2.repository.UserRepository;
import com.example.oauth2.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;
// test token eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJYaWFvbmluZyBCdSIsInJvbGUiOiJhZG1pbiIsImVtYWlsIjoieGIyMTcxQGNvbHVtYmlhLmVkdSIsImlhdCI6MTcwMDA3MjAzNSwiZXhwIjoxNzAwMTU4NDM1fQ.fDquR8bV77TlD-Yd_A4d4afaOlC7I-xu3qzKiWXUGrs
@RestController // This means that this class is a Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }


    @GetMapping(path = "/register_role/{role}") // Map ONLY POST Requests
    public @ResponseBody ResponseEntity<String>  setRole(@AuthenticationPrincipal OAuth2User oauth2User, @PathVariable String role) {
        // register this user as the given role in the database, user chooses their role after logging in with google
        User user = new User();
        user.setName(oauth2User.getAttribute("name"));
        user.setEmail(oauth2User.getAttribute("email"));
        user.setRole(role);
        userRepository.save(user);
        String jwt = JwtUtil.generateToken(user);
        return ResponseEntity.ok(jwt);
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }
}
