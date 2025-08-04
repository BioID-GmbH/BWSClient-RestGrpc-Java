package com.bws.restgrpcforwarder.grpc;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;

/**
 * Client interceptor that adds custom metadata headers to gRPC requests.
 * This interceptor copies the "Reference-Number" header if present in requestHeaders.
 */
public final class HeaderClientInterceptor implements ClientInterceptor {

    private final Metadata requestHeaders;

    /**
     * Creates a HeaderClientInterceptor with the given metadata.
     * @param requestHeaders Metadata to be added to each request.
     */
    public HeaderClientInterceptor(Metadata requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> method,
            CallOptions callOptions,
            Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                Metadata.Key<String> metaKey = Metadata.Key.of("Reference-Number", Metadata.ASCII_STRING_MARSHALLER);
                String refValue = requestHeaders.get(metaKey);
                if (refValue != null && !headers.containsKey(metaKey)) {
                    headers.put(metaKey, refValue);
                }
                super.start(responseListener, headers);
            }
        };
    }
}
