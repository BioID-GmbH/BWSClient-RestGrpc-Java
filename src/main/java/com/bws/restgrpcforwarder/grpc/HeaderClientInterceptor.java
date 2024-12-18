package com.bws.restgrpcforwarder.grpc;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.ForwardingClientCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;

/**
 * A client interceptor that adds custom metadata headers to gRPC requests.
 */
public class HeaderClientInterceptor implements ClientInterceptor {

    private final Metadata requestHeaders;

    /**
     * Constructor that receives the metadata to be added with each request.
     * 
     * @param requestHeaders The metadata to be added.
     */
    public HeaderClientInterceptor(Metadata requestHeaders) {
        this.requestHeaders = requestHeaders;
    }
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
            MethodDescriptor<ReqT, RespT> method,
            CallOptions callOptions,
            Channel next) {

        // Use ForwardingClientCall to pass the headers correctly to the call.
        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                Metadata.Key<String> metaKey = Metadata.Key.of("Reference-Number", Metadata.ASCII_STRING_MARSHALLER);
                if (!headers.containsKey(metaKey)) {
                    headers.put(metaKey, requestHeaders.get(metaKey));
                }
                super.start(responseListener, headers);
            }
        };
    }
}
