package com.system.journal.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    public static final String SECRET_KEY = "GECi3j4$+Kr35ji47IEti4o3-4iOI&$IJ%jg";
    public static final String REFRESH_SECRET_KEY = "KJ#$-4=5IJ#$INI@##$@234235J#@IJsdg";

    private SecretKey getSigningKey(String secretKey) {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String extractUsername(String token,String secret) {
        Claims claims = extractAllClaims(token,secret);
        return claims.getSubject();
    }

    public Date extractExpiration(String token,String secret) {
        return extractAllClaims(token,secret).getExpiration();
    }

    private Claims extractAllClaims(String token,String secret) {
        return Jwts.parser()
                .verifyWith(getSigningKey(secret))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token,String secret) {
        return extractExpiration(token,secret).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username,5,SECRET_KEY);
    }

    public String generateRefreshToken(String username){
        Map<String,Object> claims = new HashMap<>();
        claims.put("toekenType","refreshToken");
        return createToken(claims,username,60,REFRESH_SECRET_KEY);
    }

    private String createToken(Map<String, Object> claims, String subject,int minutes,String secretKey) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .header().empty().add("typ","JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * minutes)) // 5 minutes expiration time
                .signWith(getSigningKey(secretKey))
                .compact();
    }

    public Boolean validateToken(String token,String secret) {
        return !isTokenExpired(token,secret);
    }


}
