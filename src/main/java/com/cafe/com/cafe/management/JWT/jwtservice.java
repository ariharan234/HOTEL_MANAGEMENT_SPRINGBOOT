package com.cafe.com.cafe.management.JWT;

import com.cafe.com.cafe.management.Entity.Role;
import com.cafe.com.cafe.management.Entity.UserEntity;
import com.cafe.com.cafe.management.dto.Userdto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class jwtservice {
    private final String secretkey="51a539bc2272750640e7882f8d5ca5e7e80d430e7487cdc0371b9c4033f2d88f";



    public String extractusername(String token){

        return extractClaims(token,Claims::getSubject);
    }

    public boolean isValid(String token, UserDetails user){
        String username =extractusername(token);
        String username1=user.getUsername();
        return (username.equals(user.getUsername())) && !isTokenExpired(token);
    }
    public String createteToken(String subject, Role role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return generateToken(claims,subject);
    }
    private boolean isTokenExpired(String token) {
        return extractexpiration(token).before(new Date());
    }

    private Date extractexpiration(String token) {
        return extractClaims(token,Claims::getExpiration);
    }

    public <T> T extractClaims(String token, Function<Claims,T> resolver){
        Claims claims=extractAllClaims(token);
        return resolver.apply(claims);

    }
    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(Map<String, Object> claim,String username){
        String token= Jwts
                .builder()
                .claims(claim)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 24*60*60*1000))
                .signWith(getSignKey())
                .compact();
        return token;
    }
    private SecretKey getSignKey(){
        byte[] keyBytes= Decoders.BASE64URL.decode(secretkey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

