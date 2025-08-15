package br.com.librumbr.services;

import br.com.librumbr.exceptions.InvalidTokenException;
import br.com.librumbr.models.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("librumbr")
                    .withSubject(user.getEmail())
                    .withClaim("roles", List.of(user.getAuthorities()).toString())
                    .withExpiresAt(this.genExpirationDate(2, "HOURS"))
                    .sign(algorithm);
        } catch (JWTCreationException e){
            throw new RuntimeException("Error while generating token", e);
        }
    }

    public String generatePasswordResetToken(String email) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("librumbr-reset")
                    .withSubject(email)
                    .withExpiresAt(this.genExpirationDate(15, "MINUTES"))
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while generating password reset token", e);
        }
    }

    public String validateToken(String token){
        try {
          Algorithm algorithm = Algorithm.HMAC256(secret);
          return JWT.require(algorithm)
                  .withIssuer("librumbr", "librumbr-reset")
                  .build()
                  .verify(token)
                  .getSubject();
        } catch (JWTVerificationException e){
            throw new InvalidTokenException("Invalid or expired token");
        }
    }

    private Instant genExpirationDate(long time, String unit){
        if ("MINUTES".equalsIgnoreCase(unit)) {
            return LocalDateTime.now().plusMinutes(time).toInstant(ZoneOffset.of("-03:00"));
        }
        else return LocalDateTime.now().plusHours(time).toInstant(ZoneOffset.of("-03:00"));
    }
}
