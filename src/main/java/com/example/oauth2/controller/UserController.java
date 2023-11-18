package com.example.oauth2.controller;

import com.example.oauth2.exception.RoleNotAuthorizedException;
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
import java.util.Optional;

// test token eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJYaWFvbmluZyBCdSIsInJvbGUiOiJhZG1pbiIsImVtYWlsIjoieGIyMTcxQGNvbHVtYmlhLmVkdSIsImlhdCI6MTcwMDA3MjAzNSwiZXhwIjoxNzAwMTU4NDM1fQ.fDquR8bV77TlD-Yd_A4d4afaOlC7I-xu3qzKiWXUGrs
@RestController // This means that this class is a Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/self")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

    @GetMapping(path = "/register_role/{role}") // Map ONLY POST Requests
    public @ResponseBody ResponseEntity<String>  setRole(@AuthenticationPrincipal OAuth2User oauth2User, @PathVariable String role) {
        // register this user as the given role in the database, user chooses their role after logging in with google
        String name = oauth2User.getAttribute("name");
        String email = oauth2User.getAttribute("email");
        if (!role.equals("student") && !role.equals("professor")){
            if (role.equals("admin")){
                if (!userRepository.existsByEmailAndRole(email,"admin")){
                    throw new RoleNotAuthorizedException("you are not authorized for this role");
                }
            }else{
                throw new RoleNotAuthorizedException("the role you specified is not available");
            }
        }else{
            if (!userRepository.existsByEmailAndRole(email,role)) {
                User user = new User();
                user.setName(name);
                user.setEmail(email);
                user.setRole(role);
                userRepository.save(user);
            }
        }

        String jwt = JwtUtil.generateToken(name,email,role);
        return ResponseEntity.ok(jwt);
    }

    @GetMapping(path = "/user/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }
    /*GET /api/user/student*/
    @GetMapping(path = "/student/all")
    public @ResponseBody Iterable<User> getAllStudents() {
        // This returns a JSON or XML with the users
        return userRepository.findAllByRole("student");
    }
    @GetMapping(path = "/prof/all")
    public @ResponseBody Iterable<User> getAllProfessors() {
        // This returns a JSON or XML with the users
        return userRepository.findAllByRole("professor");
    }

    @GetMapping(path = "/user/{id}")
    public @ResponseBody
    Optional<User> getUserById(@PathVariable("id") Integer id) {
        // This returns a JSON or XML with the users
        return userRepository.findById(id);
    }





}
