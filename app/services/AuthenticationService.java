package services;

import akka.actor.ActorPath;
import akka.actor.ActorRef;
import com.typesafe.config.Config;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import io.jsonwebtoken.impl.DefaultJwtParser;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

/**
 * Created by moura on 07/04/17.
 */
@Singleton
public class AuthenticationService extends ActorRef {

    private Config config;

    @Inject
    public AuthenticationService(Config config) {
        this.config = config;
    }

    public String getSecret() {

        String userId = UUID.randomUUID().toString();

        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

        return new DefaultJwtBuilder()
                .setSubject(userId)
                .setIssuedAt(Date.from(now.toInstant(ZoneOffset.UTC)))
                .setExpiration(Date.from(now.plusHours(1).toInstant(ZoneOffset.UTC)))
                .signWith(SignatureAlgorithm.HS256, config.getString("play.http.secret.key").getBytes())
                .compressWith(CompressionCodecs.GZIP)
                .compact();
    }

    public Claims validateSecret(String secret) {
        JwtParser jwtParser = new DefaultJwtParser().setSigningKey(config.getString("play.http.secret.key").getBytes());
        Jws<Claims> claims = jwtParser.parseClaimsJws(secret);
        return claims.getBody();
    }

    @Override
    public ActorPath path() {
        return null;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }
}
