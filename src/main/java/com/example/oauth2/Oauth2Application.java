package com.example.oauth2;

import com.example.oauth2.model.User;
import com.example.oauth2.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.oauth2.util.JwtUtil;


import java.util.Collections;
import java.util.Map;

@SpringBootApplication
@RestController
public class Oauth2Application {
    private UserRepository userRepository;
    /*
    /oauth2/authorization/google initializes the sign on process,After successful authentication, the OAuth2 provider redirects the user back to the specified callback URL.
    Spring Security intercepts the callback request and completes the OAuth2 authorization process:
    Exchanges the authorization code received from the OAuth2 provider for an access token.
    Retrieves user details from the OAuth2 provider.
    Creates an OAuth2User representing the authenticated user.
    */
    //@GetMapping("/login/oauth2/code/google")



    public static void main(String[] args) {
        SpringApplication.run(Oauth2Application.class, args);
    }
}
