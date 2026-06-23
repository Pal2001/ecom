package com.pal.ecom.api.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.pal.ecom.model.LocalUser;
import com.pal.ecom.model.dao.LocalUserDAO;
import com.pal.ecom.service.JWTService;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    @Autowired
    LocalUserDAO localUserDAO;
    @Autowired
    JWTService jwtService;
    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring(7);
            try {
                String userName = jwtService.getUserName(token);
                Optional<LocalUser> opUser = localUserDAO.findByUsernameIgnoreCase(userName);
                if(opUser.isPresent()){
                    LocalUser user = opUser.get();
                    if(user.isEmailVerified()) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
            catch (JWTDecodeException ex){

            }
        }
        filterChain.doFilter(request, response);
    }
}
