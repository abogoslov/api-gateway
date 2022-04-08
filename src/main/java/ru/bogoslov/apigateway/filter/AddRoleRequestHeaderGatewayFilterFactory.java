package ru.bogoslov.apigateway.filter;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.GatewayToStringStyler;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.bogoslov.apigateway.config.JwtConfig;
import ru.bogoslov.apigateway.util.JwtUtils;

public class AddRoleRequestHeaderGatewayFilterFactory
        extends AbstractGatewayFilterFactory<AddRoleRequestHeaderGatewayFilterFactory.AddRoleConfig> {

    private final JwtConfig jwtConfig;

    public AddRoleRequestHeaderGatewayFilterFactory(JwtConfig jwtConfig) {
        super(AddRoleConfig.class);
        this.jwtConfig = jwtConfig;
    }

    @Override
    public GatewayFilter apply(AddRoleConfig config) {
        return new GatewayFilter() {

            private final String headerName = config.getHeaderName();

            private String role;

            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                role = JwtUtils.extractScope(
                        jwtConfig.getTokenSigningKey(), jwtConfig.getTokenIssuer(), JwtUtils.extractJwt(exchange)
                );

                final ServerHttpRequest mutatedRequest = exchange.getRequest()
                        .mutate()
                        .headers((httpHeaders) -> httpHeaders.add(headerName, role))
                        .build();

                final ServerWebExchange mutatedExchange = exchange.mutate()
                        .request(mutatedRequest)
                        .build();

                return chain.filter(mutatedExchange);
            }

            public String toString() {
                return GatewayToStringStyler.filterToStringCreator(AddRoleRequestHeaderGatewayFilterFactory.this)
                        .append(headerName, role)
                        .toString();
            }
        };
    }

    @Data
    public static class AddRoleConfig {

        private String headerName;
    }
}
