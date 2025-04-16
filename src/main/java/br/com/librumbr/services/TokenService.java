package br.com.librumbr.services;

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
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException e){
            throw new RuntimeException("Error while generating token", e);
        }
    }

    public String validateToken(String token){
        try {
          Algorithm algorithm = Algorithm.HMAC256(secret);
          return JWT.require(algorithm)
                  .withIssuer("librumbr")
                  .build()
                  .verify(token)
                  .getSubject();
        } catch (JWTVerificationException e){
            throw new RuntimeException("Error validating token");
        }
    }

    private Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
