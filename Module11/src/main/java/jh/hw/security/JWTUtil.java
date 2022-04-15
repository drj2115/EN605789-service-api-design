package jh.hw.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.security.SecureRandom;
import java.sql.Date;
import java.time.LocalDate;

public class JWTUtil {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final int JWT_SECRET_STREAM_SIZE = 50;
    private static final String JWT_SECRET = SECURE_RANDOM.ints(JWT_SECRET_STREAM_SIZE).toString();
    private static final String ISSUER = "DALLAS-JHU-HOMEWORK";

    public static String generateToken(String subjectName, String claimName, String claim) {
        return JWT.create()
                .withSubject(subjectName)
                .withClaim(claimName, claim)
                .withIssuedAt(Date.valueOf(LocalDate.now()))
                .withIssuer(ISSUER)
                .sign(Algorithm.HMAC512(JWT_SECRET));
    }

    public static String validateAndRetrieveSubject(String token, String subjectName, String claimName) {
        if (token == null || subjectName == null || claimName == null) {
            return null;
        }
        return JWT.require(Algorithm.HMAC512(JWT_SECRET))
                .withSubject(subjectName)
                .withIssuer(ISSUER)
                .build()
                .verify(token)
                .getClaim(claimName)
                .asString();
    }
}