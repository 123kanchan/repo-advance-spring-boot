package com.example.demo1.service.impl;

import com.example.demo1.exception.BlogAPIException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtsecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtexpirationdate;

    //generte jwt token
    public String generatetoken(Authentication authentication){
        String username=authentication.getName();
        Date Currentddate=new Date();
        Date expiredate=new Date(Currentddate.getTime()+jwtexpirationdate);
    String token= Jwts.builder().subject(username).issuedAt(new Date()).expiration(expiredate).signWith(key()).compact();
    return token;
    }
    private Key  key(){
       return  Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtsecret));
    }

    //get username from jwt token
    public String getUsername(String token){
        return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token).getPayload().getSubject();
    }

    //validate jwttoken
    public Boolean validateToken(String token) {
        try{Jwts.parser().verifyWith((SecretKey)key()).build().parse(token);
        return true;} catch (MalformedJwtException e) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"invalid jwt token");
        }
        catch(ExpiredJwtException expiredJwtException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"expired jwt token");

        }
        catch(UnsupportedJwtException unsupportedJwtException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"unsupported jwt token");

        }
        catch(IllegalArgumentException illegalArgumentException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"jwt claims string is empty ");
        }
    }
}
