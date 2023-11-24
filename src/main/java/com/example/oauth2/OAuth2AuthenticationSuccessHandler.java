package com.example.oauth2;

import com.example.oauth2.model.User;
import com.example.oauth2.repository.UserRepository;
import com.example.oauth2.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private UserRepository userRepository;
    public OAuth2AuthenticationSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        DefaultOidcUser oidcUser = (DefaultOidcUser) oauthToken.getPrincipal();
        System.out.println(oidcUser);
        String userEmail = oidcUser.getAttribute("email");
        Optional<User> result = this.userRepository.findByEmail(userEmail);
        if (result.isPresent()) {
            User user = result.get();
            if (user.getName()==null){
                user.setName(oidcUser.getAttribute("name"));
                userRepository.save(user);
            }
            String jwt = JwtUtil.generateToken(user.getName(),user.getEmail(),user.getRole());
            response.getWriter().write(jwt);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Email not found");
        }
    }
}
