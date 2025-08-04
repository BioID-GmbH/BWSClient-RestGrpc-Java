
package com.bws.restgrpcforwarder.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration class for gRPC client settings.
 * This class loads the necessary properties for connecting to the gRPC API.
 */
@Configuration
@ConfigurationProperties(prefix = "grpcapi")
public class GrpcClientConfig {
    
    /**
     * The endpoint of the gRPC API.
     */
    private String endpoint;

    /**
     * The client ID for the gRPC API.
     */
    private String clientId;

    /**
     * The access key (secret) for the gRPC API.
     */
    private String accessKey;

    /**
     * The audience for the JWT token.
     */
    @org.springframework.beans.factory.annotation.Value("${jwt.audience}")
    private String audience;

    /**
     * The expiration time of the JWT token in minutes.
     */
    @org.springframework.beans.factory.annotation.Value("${jwt.expirationInMinutes}")
    private Long expirationInMinutes;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public Long getExpirationInMinutes() {
        return expirationInMinutes;
    }

    public void setExpirationInMinutes(Long expirationInMinutes) {
        this.expirationInMinutes = expirationInMinutes;
    }
}
