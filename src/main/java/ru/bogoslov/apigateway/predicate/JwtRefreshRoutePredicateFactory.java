package ru.bogoslov.apigateway.predicate;

import lombok.Data;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.web.server.ServerWebExchange;
import ru.bogoslov.apigateway.service.JwtVerifyService;
import ru.bogoslov.apigateway.util.JwtUtils;

import java.util.Base64;
import java.util.function.Predicate;

public class JwtRefreshRoutePredicateFactory
        extends AbstractRoutePredicateFactory<JwtRefreshRoutePredicateFactory.Config> {

    private final JwtVerifyService jwtAuthPredicateService;

    public JwtRefreshRoutePredicateFactory(JwtVerifyService jwtAuthPredicateService) {
        super(Config.class);
        this.jwtAuthPredicateService = jwtAuthPredicateService;
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        return (ServerWebExchange t) -> jwtAuthPredicateService.isValidRefreshJwt(config, JwtUtils.extractJwt(t));
    }

    @Data
    public static class Config {

        private String tokenIssuer;
        private String tokenSigningKey;
        private Integer refreshTokenExpirationTime;

        @SuppressWarnings("unused")
        public byte[] getTokenSigningKey() {
            if (tokenSigningKey == null) {
                return null;
            }
            return Base64.getDecoder()
                    .decode(tokenSigningKey);
        }
    }
}
