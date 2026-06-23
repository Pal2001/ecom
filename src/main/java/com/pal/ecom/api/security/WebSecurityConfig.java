package com.pal.ecom.api.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;

@Configuration
public class WebSecurityConfig {
    @Autowired
    JWTRequestFilter jwtRequestFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())
            .addFilterBefore(jwtRequestFilter, AuthenticationFilter.class)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/product", "/auth/register", "/auth/login", "/auth/verify", "/error").permitAll()
                .anyRequest().authenticated());
        return http.build();


    }
}
