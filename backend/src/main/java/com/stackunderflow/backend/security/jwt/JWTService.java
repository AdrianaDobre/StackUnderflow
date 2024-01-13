package com.stackunderflow.backend.security.jwt;

import com.stackunderflow.backend.DTOS.TokenDTO;
import com.stackunderflow.backend.repository.UserRepository;
import com.stackunderflow.backend.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JWTService {
    //daca injectez UserService am dependinta circulara
    final UserRepository userRepository;
    @Value("${spring.jwt.secret}")
    private String JWT_SECRET;

    @Value("${spring.jwt.jwtExpirationInMs}")
    private int JWT_EXPIRATION_TIME_IN_MILLISECONDS;

    public TokenDTO generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return tokenCreator(claims, userName);
    }

    public TokenDTO tokenCreator(Map<String, Object> claims, String userName) {
        long currentDate = System.currentTimeMillis();
        claims.put("id", userRepository.findByEmail(userName).orElseThrow(() -> new UsernameNotFoundException("Not found")).getId());
        String token = Jwts.builder()
                .claims(claims)
                .subject(userName)
                .issuedAt(new Date(currentDate))
                .expiration(new Date(currentDate + JWT_EXPIRATION_TIME_IN_MILLISECONDS))
                .signWith(getSignedKey())
                .compact();
        return new TokenDTO(token);
    }

    public String extractUsernameFromToken(String theToken) {
        return extractClaim(theToken, Claims::getSubject);
    }

    public Date extractExpirationTimeFromToken(String theToken) {
        return extractClaim(theToken, Claims::getExpiration);
    }

    public Boolean validateToken(String theToken, UserDetails userDetails) {
        final String userName = extractUsernameFromToken(theToken);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(theToken));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignedKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    private boolean isTokenExpired(String theToken) {
        return extractExpirationTimeFromToken(theToken).before(new Date());
    }

    private SecretKey getSignedKey() {
        byte[] keyByte = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyByte);
    }
}
