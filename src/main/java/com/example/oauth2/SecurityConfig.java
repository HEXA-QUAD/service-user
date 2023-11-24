package com.example.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RestController;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private final OAuth2AuthenticationSuccessHandler Oauth2Handler;
        public SecurityConfig(OAuth2AuthenticationSuccessHandler Oauth2Handler) {
                this.Oauth2Handler = Oauth2Handler;
        }
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http
                    .authorizeRequests(a -> a
                            .antMatchers(HttpMethod.POST, "/student_history/add/**").permitAll()
                            .antMatchers("/", "/error", "/webjars/**", "/register_role", "/student/**","/student_history/**","/user/**","/prof/**","/login/**").permitAll()
                            .anyRequest().authenticated()
                    )
                    .exceptionHandling(e -> e
                            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                    )
                    .oauth2Login()
                    .successHandler(this.Oauth2Handler);
        }
}
