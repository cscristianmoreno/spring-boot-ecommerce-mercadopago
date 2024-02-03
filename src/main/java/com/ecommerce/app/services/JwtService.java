package com.ecommerce.app.services;

import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ecommerce.app.dto.UsersDTO;
import com.ecommerce.app.utils.InstantUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;

@Service
public class JwtService {

    @Value("${jwt.token.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expire}")
    private int expire;

    public String generateToken(UsersDTO user) {

        Long currentMillis = InstantUtil.getCurrentMillis();
        Long expiration = InstantUtil.setCurrentMillis(expire);

        String jwt = Jwts
        .builder()
        .header()
            .add("typ", "JWT")
        .and()
        .claim("id", user.getId())
        .claim("email", user.getEmail())
        .issuer(user.getEmail())
        .issuedAt(new Date(currentMillis))
        .expiration(new Date(expiration))
        .signWith(secretKeyEncode())
        .compact();

        return jwt;
    }
    

    public Claims decodeToken(String token) throws JwtException {
        Claims claims = Jwts.parser().verifyWith(secretKeyEncode()).build().parseSignedClaims(token).getPayload();
        return claims;
    }

    public SecretKey secretKeyEncode() {
        byte[] decode = Decoders.BASE64.decode(secretKey);
        SecretKey secret = new SecretKeySpec(decode, "HmacSHA256");
        return secret;
    }
}
