package com.example.oauth2.controller;

import com.example.oauth2.exception.NoSuchAccountException;
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

    @PostMapping(path = "/register_role") // Map ONLY POST Requests
    public @ResponseBody String setRole(@RequestParam String email, @RequestParam String role) {
        // register this user as the given role in the database, user chooses their role after logging in with google
        if (!role.equals("student") && !role.equals("professor")){
            if (role.equals("admin")){
                if (!userRepository.existsByEmailAndRole(email,"admin")){
                    throw new RoleNotAuthorizedException("you are not authorized for this role");
                }else{
                    return "registered";
                }
            }else{
                throw new RoleNotAuthorizedException("the role you specified is not available");
            }
        }else{
            Optional<User> result= userRepository.findByEmail(email);
            if (result.isEmpty()) {
                User user = new User();
                user.setEmail(email);
                user.setRole(role);
                userRepository.save(user);
                return "registered";
            }
            return "this email is already in use and registered";
        }

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

    @GetMapping(path = "/user/{email}")
    public @ResponseBody
    User getUserById(@PathVariable("email") String email) {
        // This returns a JSON or XML with the users
        Optional<User> result = userRepository.findByEmail(email);
        if (result.isPresent()){
            return result.get();
        }else{
            throw new NoSuchAccountException("user with email "+email+" does not exist please register first");
        }
    }
}
