package ac.th.ku.soa.washsystem.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
 private String secretKey = "feel";

 public String generateToken(String username) {
     return Jwts.builder()
             .setSubject(username)
             .setIssuedAt(new Date())
             .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
             .signWith(SignatureAlgorithm.HS256, secretKey)
             .compact();
 }

 public String extractUsername(String token) {
     return Jwts.parser()
             .setSigningKey(secretKey)
             .parseClaimsJws(token)
             .getBody()
             .getSubject();
 }

 public boolean validateToken(String token, String usernameval) {
     final String username = extractUsername(token);
     return (username.equals(usernameval) && !isTokenExpired(token));
 }

 private boolean isTokenExpired(String token) {
     return Jwts.parser()
             .setSigningKey(secretKey)
             .parseClaimsJws(token)
             .getBody()
             .getExpiration()
             .before(new Date());
 }
}

