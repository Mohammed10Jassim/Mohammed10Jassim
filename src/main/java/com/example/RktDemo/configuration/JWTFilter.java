package com.example.RktDemo.configuration;

import com.example.RktDemo.Service.JWTService;
import com.example.RktDemo.Service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    private JWTService jwtService;
    @Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJTVEYiLCJpYXQiOjE3NDk1NTY4ODl9.SHKuWpw68ptQ5LJm8Nu6-UPLFGEf-fu26fRQ2jiHmCY
        String authHeader = request.getHeader("Authorization");
        String token  = null;
        String username = null;
        //Validation of the token by extracting it from the header
        if(authHeader!=null && authHeader.startsWith("Bearer "))
        {
            token = authHeader.substring(7);
            username = jwtService.extractUserName(token);
        }
        //Validation of Username and Token
        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
        {
            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
            if(jwtService.validateToken(token,userDetails))
            {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                //we need to let know  the auth token about the request details
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //setting this token is application context
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
        }
        // Then well go for the next filter
        filterChain.doFilter(request,response);


    }
}
