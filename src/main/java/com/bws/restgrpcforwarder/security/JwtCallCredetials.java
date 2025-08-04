package com.bws.restgrpcforwarder.security;

import java.util.concurrent.Executor;
import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.Status;

/**
 * CallCredentials implementation for JWT authentication.
 * Applies the JWT token to gRPC request metadata.
 */
public final class JwtCallCredetials extends CallCredentials {

    private final String token;

    /**
     * Constructs JwtCallCredetials with the provided JWT token.
     * @param token JWT token
     */
    public JwtCallCredetials(String token) {
        this.token = token;
    }

    /**
     * Applies the JWT token to the gRPC request metadata.
     * @param requestInfo request information
     * @param appExecutor executor for async operations
     * @param applier metadata applier
     */
    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor appExecutor, MetadataApplier applier) {
        appExecutor.execute(() -> {
            try {
                Metadata headers = new Metadata();
                Metadata.Key<String> authKey = Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);
                headers.put(authKey, "Bearer " + token);
                applier.apply(headers);
            } catch (Exception e) {
                applier.fail(Status.UNAUTHENTICATED.withDescription("Failed to apply JWT token").withCause(e));
            }
        });
    }
}
