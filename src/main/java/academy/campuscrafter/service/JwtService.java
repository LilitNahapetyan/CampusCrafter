package academy.campuscrafter.service;
import academy.campuscrafter.config.properties.JwtConfigProperties;
import academy.campuscrafter.model.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.io.Decoders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String USER_ID = "userId";
    private static final String NAME = "name";
    private static final String ROLE = "role";
    private final JwtConfigProperties jwtConfigProperties;

    public String generateToken(User user) {
        byte[] keyBytes = Decoders.BASE64.decode(jwtConfigProperties.getSecretKey());
        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim(USER_ID, user.getId().toString())
                .withClaim(NAME, user.getName())
                .withClaim(ROLE, user.getRole().toString())
                .withIssuer(jwtConfigProperties.getIssuer())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plus(jwtConfigProperties.getExpiration(), ChronoUnit.MINUTES))
                .sign(Algorithm.HMAC256(keyBytes));
    }
    public JWTVerifier jwtVerifier() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtConfigProperties.getSecretKey());
        return JWT.require(Algorithm.HMAC256(keyBytes)).build();
    }
}