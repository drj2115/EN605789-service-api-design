package jh.hw.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Date;
import java.time.LocalDate;

@Component
public class JWTUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JWTUtil.class);
    private static final int BUFFER_SIZE = 50;
    private static final String JWT_SECRET;
    static {
        byte[] byteBuffer = new byte[BUFFER_SIZE];
        new SecureRandom().nextBytes(byteBuffer);
        JWT_SECRET = new BigInteger(1, byteBuffer).toString(Character.MAX_RADIX);
        LOGGER.info("JWT_SECRET set to {}", JWT_SECRET);
    }

    @Value("${security.auth.jwt.issuer}")
    private String issuer;

    public String generateToken(String subjectName, String claimName, String claim) {
        return JWT.create()
                .withSubject(subjectName)
                .withClaim(claimName, claim)
                .withIssuedAt(Date.valueOf(LocalDate.now()))
                .withIssuer(issuer)
                .sign(Algorithm.HMAC512(JWT_SECRET));
    }

    public String validateAndRetrieveSubject(String token, String subjectName, String claimName) {
        if (token == null || subjectName == null || claimName == null) {
            return null;
        }
        return JWT.require(Algorithm.HMAC512(JWT_SECRET))
                .withSubject(subjectName)
                .withIssuer(issuer)
                .build()
                .verify(token)
                .getClaim(claimName)
                .asString();
    }
}