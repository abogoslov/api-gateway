package ru.bogoslov.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.bogoslov.apigateway.predicate.JwtAccessRoutePredicateFactory;
import ru.bogoslov.apigateway.predicate.JwtRefreshRoutePredicateFactory;
import ru.bogoslov.apigateway.service.JwtVerifyService;

@Configuration
public class PredicateConfig {

    @Bean
    public JwtRefreshRoutePredicateFactory jwtRefresh(JwtVerifyService jwtAuthPredicateService) {
        return new JwtRefreshRoutePredicateFactory(jwtAuthPredicateService);
    }

    @Bean
    public JwtAccessRoutePredicateFactory jwtAccess(JwtVerifyService jwtAuthPredicateService) {
        return new JwtAccessRoutePredicateFactory(jwtAuthPredicateService);
    }
}
