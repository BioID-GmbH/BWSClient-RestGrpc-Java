package com.bws.restgrpcforwarder.datatypes;

import com.bioid.services.Bws.LivenessDetectionResponse;
import io.grpc.Metadata;

/**
 * A DTO that contains a gRPC API response object with metadata.
 * 
 * The LivenessDetectionResult class encapsulates the result of a LivenessDetection or VideoLivenessDetection operation. 
 * It includes both the response from the detection process and any associated metadata.
 * 
 */
public class LivenessDetectionResult {

    private LivenessDetectionResponse response;
    private Metadata metadata;

    /**
     * Constructs a LivenessDetectionResult with the provided response and metadata.
     *
     * @param response the response of the livenessDetection or videoLivenessDetection
     * @param metadata the metadata associated with the livenessDetection or videoLivenessDetection
     */
    public LivenessDetectionResult(LivenessDetectionResponse response, Metadata metadata) {
        this.response = response;
        this.metadata = metadata;
    }
    public LivenessDetectionResult(LivenessDetectionResponse response) {
        this.response = response;

    }

    // Getter und Setter
    public LivenessDetectionResponse getResponse()
    {
        return response;
    }

    public void setResponse(LivenessDetectionResponse response)
    {
        this.response = response;
    }

    public Metadata getMetadata()
    {
        return metadata;
    }

    public void setMetadata(Metadata metadata)
    {
        this.metadata = metadata;
    }
}
