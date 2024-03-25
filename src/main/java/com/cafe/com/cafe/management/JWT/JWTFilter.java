package com.cafe.com.cafe.management.JWT;

import com.cafe.com.cafe.management.dto.Userdto;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Component
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    private jwtservice jwtservice;
    @Autowired
    private CustomerUserDetails userdetailsservice;
    Claims claim=null;
    String username=null;

    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authheasder=request.getHeader("Authorization");

        if(authheasder==null|| !authheasder.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        String token=authheasder.substring(7);
        username =jwtservice.extractusername(token);
        claim=jwtservice.extractAllClaims(token);

        if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails1=userdetailsservice.loadUserByUsername(username);

            if(jwtservice.isValid(token,userDetails1)){
                UsernamePasswordAuthenticationToken authtoken=new UsernamePasswordAuthenticationToken(userDetails1,null,userDetails1.getAuthorities());
                authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authtoken);


            }
            else{

            }
        }
        filterChain.doFilter(request,response);
    }

    public boolean isAdmin(){
        return "admin".equalsIgnoreCase((String) claim.get("role"));
    }
    public boolean isUser(){
        return "user".equalsIgnoreCase((String) claim.get("role"));
    }
    public String getCurrentUser(){
        return username;
    }

}


