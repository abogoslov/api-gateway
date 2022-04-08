package ru.bogoslov.apigateway.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Verification;
import org.springframework.stereotype.Component;
import ru.bogoslov.apigateway.config.JwtConfig;
import ru.bogoslov.apigateway.predicate.JwtAccessRoutePredicateFactory;
import ru.bogoslov.apigateway.predicate.JwtRefreshRoutePredicateFactory;

@Component
public class JwtVerifyService {

    public boolean isValidRefreshJwt(JwtRefreshRoutePredicateFactory.Config config, String jwt) {
        verifyJwt(config.getTokenSigningKey(), config.getTokenIssuer(), "REFRESH", jwt);
        return true;
    }

    public boolean isValidAccessJwt(JwtAccessRoutePredicateFactory.Config config, String jwt) {
        verifyJwt(config.getTokenSigningKey(), config.getTokenIssuer(), "ACCESS", jwt);
        return true;
    }

    public boolean isValidJwt(JwtConfig config, String jwt) {
        verifyJwt(config.getTokenSigningKey(), config.getTokenIssuer(), null, jwt);
        return true;
    }

    private void verifyJwt(byte[] tokenSigningKey, String tokenIssuer, String scope, String jwt) {
        Verification verification = JWT.require(Algorithm.HMAC256(tokenSigningKey))
                .withIssuer(tokenIssuer);

        if (scope != null) {
            verification = verification.withClaim("scope", scope);
        }

        verification.build()
                .verify(jwt);
    }
}
