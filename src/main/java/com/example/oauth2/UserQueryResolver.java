package com.example.oauth2;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.oauth2.model.User;
import com.example.oauth2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserQueryResolver implements GraphQLQueryResolver {

    private final UserRepository userRepository;

    @Autowired
    public UserQueryResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User userByEmail(String email) {
        return userRepository.findById(email).orElse(null);
    }
}