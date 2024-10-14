package com.bws.restgrpcforwarder.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for gRPC client settings.
 * This class loads the necessary properties for connecting to the gRPC API.
 */
@Configuration
public class GrpcClientConfig {
    
    /**
     * The endpoint of the gRPC API.
     */
    @Value("${grpcApi.endpoint}")
    public String serviceEndpoint;

    /**
     * The client ID for the gRPC API.
     */
    @Value("${grpcApi.clientId}")
    public String clientId;

    /**
     * The access key (secret) for the gRPC API.
     */
    @Value("${grpcApi.secret}")
    public String accessKey;

    /**
     * The audience for the JWT token.
     */
    @Value("${jwt.audience}")
    public String audience;

    /**
     * The expiration time of the JWT token in minutes.
     */
    @Value("${jwt.expirationInMinutes}")
    public Long tokenExpirationTime;

}
