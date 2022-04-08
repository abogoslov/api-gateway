package ru.bogoslov.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.bogoslov.apigateway.filter.AddRoleRequestHeaderGatewayFilterFactory;

@Configuration
public class FilterConfig {

    @Bean
    public AddRoleRequestHeaderGatewayFilterFactory addRole(JwtConfig jwtConfig) {
        return new AddRoleRequestHeaderGatewayFilterFactory(jwtConfig);
    }
}
