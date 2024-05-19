package com.postgresql.indiegogo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

public class JwtInterceptor implements HandlerInterceptor {

    private static final String SECRET_KEY = "yourSecretKeykosmak3sg4gy4gggg66666gggsyys5yh5hyhg5h5h5yourSecretyourSecretKeykosmak3sg4gy4gggg66666gggsyys5yh5hyhg5h5h5yourSecret"; // Change this to a secure, random string

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws ServletException {
        String token = request.getHeader("Authorization");
        

        if (token == null || !isValidToken(token)) {
        	System.out.println(token);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        return true;
    }

    private boolean isValidToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
        	System.out.println(e);
            return false;
        }
    }
    



}
