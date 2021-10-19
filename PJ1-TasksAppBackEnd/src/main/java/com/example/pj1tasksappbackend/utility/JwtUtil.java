package com.example.pj1tasksappbackend.utility;

import com.example.pj1tasksappbackend.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * The type Jwt util.
 */
@Component
public class JwtUtil {

    private final Key HMAC_KEY;

    /**
     * Instantiates a new Jwt util.
     *
     * @param secret the secret
     */
    public JwtUtil(@Value("${jwt.secret.key}") String secret) {
        HMAC_KEY = new SecretKeySpec(
                Base64.getDecoder().decode(secret),
                SignatureAlgorithm.HS512.getJcaName()
        );
    }


    /**
     * Generate jwt string.
     *
     * @param user the user
     * @return the string
     */
    public String generateJwt(User user){

        return Jwts.builder()
            .setId(UUID.randomUUID().toString())
            .setIssuer("TwoxaTasksApp")
            .claim("email", user.getEmail())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + (4 * 60 * 60 * 1000))) // 4 hours
            .signWith(HMAC_KEY)
            .compact()
            .trim();
    }


    /**
     * Validate jwt.
     *
     * @param jwtString the jwt string
     * @throws SignatureException  the signature exception
     * @throws ExpiredJwtException the expired jwt exception
     */
//SignatureException when the JWT is tampered
    //ExpiredJwtException when the JWT is expired
    public void validateJwt(String jwtString) throws SignatureException, ExpiredJwtException {
        if(jwtString.isEmpty()) {
            throw new SignatureException("The Jwt is empty");
        }
        Jwts.parserBuilder()
            .setSigningKey(HMAC_KEY)
            .build()
            .parseClaimsJws(jwtString);
    }

    /**
     * Gets jwt from header.
     *
     * @param bearerToken the bearer token
     * @return the jwt from header
     * @throws IllegalArgumentException the illegal argument exception
     */
    public String getJwtFromHeader( String bearerToken) throws IllegalArgumentException {
        if (bearerToken.startsWith("Bearer ")){
             return bearerToken.substring(7, bearerToken.length());
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Get email from jwt string.
     *
     * @param jwt the jwt
     * @return the string
     */
    public String getEmailFromJwt(String jwt){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(HMAC_KEY)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        return claims.get("email").toString();
    }
}