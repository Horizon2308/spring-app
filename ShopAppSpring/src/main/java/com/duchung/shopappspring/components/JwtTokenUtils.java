package com.duchung.shopappspring.components;

import com.duchung.shopappspring.exceptions.InvalidParameterException;
import com.duchung.shopappspring.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class JwtTokenUtils {

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.secret-key}")
    private String secretKey;

    public String generateToken(User user) throws InvalidParameterException {
        Map<String, Object> claims = new HashMap<>();
        claims.put("phoneNumber", user.getPhoneNumber());
        claims.put("userId", user.getId());
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    .setSubject(user.getPassword())
                    .signWith(this.getSigningKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            throw new InvalidParameterException("Invalid parameters");
        }
    }

    private Key getSigningKey() {
        byte[] secretKeyDecoded = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(secretKeyDecoded);
    }

    private Claims exactAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T getClaim(String token, Function<Claims, T> claimResolve) {
        final Claims claims = this.exactAllClaims(token);
        return claimResolve.apply(claims);
    }

    public String exactPhoneNumber(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        final Date dateFromToken = this.getClaim(token, Claims::getExpiration);
        return dateFromToken.before(new Date());
    }

    public boolean validateToken(String token, User user) {
        final String phoneNumberFromToken = this.exactPhoneNumber(token);
        return (phoneNumberFromToken.equals(user.getPhoneNumber()) && !isTokenExpired(token));
    }

}
