package com.taskmanager.project.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class JwtUtil {
    private static final Algorithm algorithm = Algorithm.HMAC256("segredo-seguro");

    public static String generateToken(String userId) {
        return JWT.create()
                .withSubject(userId)
                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    public static String validateAndReturnUserId(String token) {
        return JWT.require(algorithm).build().verify(token).getSubject();
    }
}