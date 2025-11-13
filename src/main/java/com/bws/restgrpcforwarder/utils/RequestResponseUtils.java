package com.bws.restgrpcforwarder.utils;

import java.util.concurrent.CompletableFuture;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import io.grpc.Metadata;
import org.springframework.http.HttpHeaders;
import org.slf4j.Logger;
import java.util.Base64;

/**
 * Utility class for common controller operations
 * 
 * Provides reusable methods for:
 * - Header extraction and validation
 * - Base64 decoding with error handling
 * - Response creation and formatting
 * - Common error handling patterns
 */
public final class RequestResponseUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseUtils.class);
    
    // Private constructor to prevent instantiation
    private RequestResponseUtils() {}
    
    /**
     * Extracts Reference-Number header from HTTP headers
     * 
     * @param headers Spring HTTP headers
     * @return Reference number value or empty string if not present
     */
    public static String extractReferenceNumber(HttpHeaders headers) {
        var referenceValue = headers.getFirst("Reference-Number");
        return (referenceValue == null) ? "" : referenceValue;
    }
    
    /**
     * Creates gRPC metadata with reference number header
     * 
     * @param referenceNumber The reference number to include in metadata
     * @return gRPC Metadata with Reference-Number header if provided
     */
    public static Metadata createReferenceHeader(String referenceNumber) {
        Metadata metadata = new Metadata();
        if (referenceNumber != null && !referenceNumber.isEmpty()) {
            Metadata.Key<String> refKey = 
                Metadata.Key.of("Reference-Number", Metadata.ASCII_STRING_MARSHALLER);
            metadata.put(refKey, referenceNumber);
        }
        return metadata;
    }

    /**
     * Safely decodes Base64 string with proper error handling
     * 
     * @param base64String The Base64 encoded string to decode
     * @param fieldName Name of the field for error messages
     * @return Decoded byte array
     * @throws IllegalArgumentException if decoding fails or input is invalid
     */
    public static byte[] safeBase64Decode(String base64String, String fieldName) {
        if (base64String == null || base64String.trim().isEmpty()) {
            return new byte[0];
        }
        
        try {
            // Validate Base64 format
            if (!isValidBase64(base64String)) {
                throw new IllegalArgumentException("Invalid Base64 format for " + fieldName);
            }
            
            return Base64.getDecoder().decode(base64String);
        } catch (IllegalArgumentException e) {
            logger.error("Base64 decoding failed for {}: {}", fieldName, e.getMessage());
            throw new IllegalArgumentException("Invalid Base64 data in " + fieldName + ": " + e.getMessage());
        }
    }
    
    /**
     * Validates if string is proper Base64 format
     * 
     * @param base64String String to validate
     * @return true if valid Base64 format
     */
    private static boolean isValidBase64(String base64String) {
        if (base64String == null || base64String.isEmpty()) {
            return false;
        }
        
        try {
            // Basic Base64 validation - check if it can be decoded
            Base64.getDecoder().decode(base64String);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    
    /**
     * Creates a standardized error response
     * 
     * @param errorMessage The error message to return
     * @param logger Logger instance for error logging
     * @param context Additional context for logging
     * @return CompletableFuture with error response
     */
    public static CompletableFuture<ResponseEntity<?>> createErrorResponse(
            String errorMessage, Logger logger, String context) {
        logger.error("Error in {}: {}", context, errorMessage);
        return CompletableFuture.completedFuture(
            ResponseEntity.badRequest().body("{\"error\": \"" + errorMessage + "\"}")
        );
    }
    
    /**
     * Creates a standardized success response with JSON body
     * 
     * @param responseBody The response body as JSON string
     * @param httpHeaders HTTP headers to include in response
     * @return CompletableFuture with success response
     */
    public static CompletableFuture<ResponseEntity<?>> createSuccessResponse(
            String responseBody, org.springframework.http.HttpHeaders httpHeaders) {
        return CompletableFuture.completedFuture(
            ResponseEntity.ok().headers(httpHeaders).body(responseBody)
        );
    }
}
