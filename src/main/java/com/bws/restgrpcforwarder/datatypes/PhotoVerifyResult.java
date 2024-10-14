package com.bws.restgrpcforwarder.datatypes;

import com.bioid.services.Bws.PhotoVerifyResponse;
import io.grpc.Metadata;

/**
 * A DTO that contains a gRPC API response object with metadata.
 * 
 * Represents the result of a photoverify process.
 * This class holds the response and metadata related to the photoverify.
 */
public class PhotoVerifyResult {

    private PhotoVerifyResponse response;
    private Metadata metadata;

    /**
     * Constructs a PhotoVerifyResult with the provided response and metadata.
     *
     * @param response the response of the photoverify.
     * @param metadata the metadata associated with the photoverify.
     */
    public PhotoVerifyResult(PhotoVerifyResponse response, Metadata metadata) {
        this.response = response;
        this.metadata = metadata;
    }

    // Getter und Setter
    public PhotoVerifyResponse getResponse()
    {
        return response;
    }

    public void setResponse(PhotoVerifyResponse response)
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
