package com.bws.restgrpcforwarder.grpc;

import org.springframework.http.HttpHeaders;
import io.grpc.Metadata;

/**
 * Utility class for converting gRPC metadata to HTTP headers.
 * This class provides a method to convert gRPC metadata to Spring's HttpHeaders.
 */
/**
 * Utility class for converting gRPC metadata to HTTP headers.
 * Provides static methods only.
 */
public final class GrpcMetadataConverter {

    // Private constructor to prevent instantiation
    private GrpcMetadataConverter() {}

    /**
     * Converts gRPC metadata to Spring HttpHeaders.
     *
     * @param metadata gRPC metadata
     * @return converted HttpHeaders
     */
    public static HttpHeaders convertMetadataToHttpHeaders(Metadata metadata) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (String key : metadata.keys()) {
            String value = metadata.get(Metadata.Key.of(key, Metadata.ASCII_STRING_MARSHALLER));
            if (value != null) {
                httpHeaders.add(key, value);
            }
        }
        return httpHeaders;
    }
}
