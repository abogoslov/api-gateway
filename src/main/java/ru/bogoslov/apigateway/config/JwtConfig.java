package ru.bogoslov.apigateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Data
@Configuration
@ConfigurationProperties(prefix = "api.jwt")
public class JwtConfig {

    private String tokenIssuer;
    private String tokenSigningKey;
    private Integer accessTokenExpirationTime;
    private Integer refreshTokenExpirationTime;

    public byte[] getTokenSigningKey() {
        if (tokenSigningKey == null) {
            return null;
        }
        return Base64.getDecoder()
                .decode(tokenSigningKey);
    }
}
