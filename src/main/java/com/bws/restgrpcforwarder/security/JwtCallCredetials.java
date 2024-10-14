package com.bws.restgrpcforwarder.security;

import java.util.concurrent.Executor;
import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.Status;

/**
 * CallCredentials implementation for JWT authentication.
 * This class applies the JWT token to the gRPC request metadata.
 */
public class JwtCallCredetials extends CallCredentials{

    private final String token;

    /**
     * Constructs JwtCallCredetials with the provided token.
     *
     * @param token the JWT token
     */
    public JwtCallCredetials(String token) {
        this.token = token;
    }

    /**
     * Applies the JWT token to the request metadata.
     *
     * @param requestInfo the request information.
     * @param appExecutor the executor for asynchronous operations.
     * @param applier the metadata applier.
     */
    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor appExecutor, MetadataApplier applier) {
        appExecutor.execute(() -> {
            try {
                Metadata headers = new Metadata();
                Metadata.Key<String> authKey = Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);
                headers.put(authKey, "Bearer " + token);
                applier.apply(headers);
            } catch (Throwable e) {
                applier.fail(Status.UNAUTHENTICATED.withCause(e));
            }
        });
    }
}
