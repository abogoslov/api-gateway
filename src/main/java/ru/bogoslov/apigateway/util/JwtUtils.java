package ru.bogoslov.apigateway.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
import java.util.Optional;

public class JwtUtils {

    public static String extractJwt(ServerWebExchange exchange) {
        final List<String> xApiCredHeaderValue = exchange.getRequest()
                .getHeaders()
                .get("x-api-cred");

        return Optional.ofNullable(xApiCredHeaderValue)
                .orElseThrow(() -> new SecurityException("Incorrect request"))
                .get(0);
    }

    public static String extractScope(byte[] tokenSigningKey, String tokenIssuer, String jwt) {
        return JWT.require(Algorithm.HMAC256(tokenSigningKey))
                .withIssuer(tokenIssuer)
                .build()
                .verify(jwt)
                .getClaim("scope")
                .asString();
    }
}
