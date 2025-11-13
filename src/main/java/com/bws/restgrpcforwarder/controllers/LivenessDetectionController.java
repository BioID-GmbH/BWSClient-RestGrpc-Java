package com.bws.restgrpcforwarder.controllers;

import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bioid.services.Bws.LivenessDetectionRequest;
import com.bioid.services.Bwsmessages.ImageData;
import com.bws.restgrpcforwarder.datatypes.LivenessDetectionRequestJson;
import com.bws.restgrpcforwarder.grpc.GrpcClientService;
import com.bws.restgrpcforwarder.grpc.GrpcMetadataConverter;
import com.bws.restgrpcforwarder.utils.RequestResponseUtils;
import com.google.protobuf.ByteString;
import com.google.protobuf.util.JsonFormat;
import io.grpc.Metadata;

/**
 * REST Controller for Liveness Detection operations
 * 
 * Provides endpoint for detecting liveness from one or two live images
 * Supports challenge-response with image tags
 */
@RestController
@RequestMapping("livenessdetection")
public class LivenessDetectionController {

    private final GrpcClientService grpcClient;
    private static final Logger logger = LoggerFactory.getLogger(LivenessDetectionController.class);

    public LivenessDetectionController(GrpcClientService bwsGrpcClient) {
        grpcClient = bwsGrpcClient;
    }

    /**
     * Processes liveness detection request
     * 
     * @param headers                  HTTP headers including optional
     *                                 Reference-Number
     * @param livenessDetectionRequest JSON request with live images
     * @return CompletableFuture with liveness detection result
     */
    @PostMapping()
    public CompletableFuture<ResponseEntity<?>> onPost(@RequestHeader HttpHeaders headers,
            @RequestBody LivenessDetectionRequestJson livenessDetectionRequest) {

        try {
            // Extract reference number using utility method
            String referenceNumber = RequestResponseUtils.extractReferenceNumber(headers);

            // Validate that we have at least one live image
            if (livenessDetectionRequest.getLiveImages().isEmpty()) {
                return RequestResponseUtils.createErrorResponse(
                        "No live images transmitted", logger, "liveness detection");
            }

            // Decode first live image
            byte[] image1 = RequestResponseUtils.safeBase64Decode(
                    livenessDetectionRequest.getLiveImages().get(0).getImage(), "first live image");

            // Create first image data
            ImageData imageData1 = ImageData.newBuilder()
                    .setImage(ByteString.copyFrom(image1))
                    .build();

            // Build base request with first image
            LivenessDetectionRequest.Builder requestBuilder = LivenessDetectionRequest.newBuilder()
                    .addLiveImages(imageData1);

            // Process second image if present
            if (livenessDetectionRequest.getLiveImages().size() > 1) {
                byte[] image2 = RequestResponseUtils.safeBase64Decode(
                        livenessDetectionRequest.getLiveImages().get(1).getImage(), "second live image");

                if (image2.length > 0) {
                    ImageData.Builder imageData2Builder = ImageData.newBuilder()
                            .setImage(ByteString.copyFrom(image2));

                    // Add tag for challenge-response if present
                    if (!livenessDetectionRequest.getLiveImages().get(1).getTags().isEmpty()) {
                        String tag = livenessDetectionRequest.getLiveImages().get(1).getTags().get(0);
                        imageData2Builder.addTags(tag);
                    }

                    requestBuilder.addLiveImages(imageData2Builder.build());
                }
            }

            // Create gRPC metadata with reference number
            Metadata referenceHeader = RequestResponseUtils.createReferenceHeader(referenceNumber);

            // Execute gRPC call
            var call = grpcClient.livenessDetectionAsync(requestBuilder.build(), referenceHeader);
            var detectionResponse = call.get();

            logger.info("Liveness detection API returned status: {}",
                    detectionResponse.getResponse().getStatus());

            // Convert and return response
            var httpHeaders = GrpcMetadataConverter.convertMetadataToHttpHeaders(detectionResponse.getMetadata());
            var responseBody = JsonFormat.printer().print(detectionResponse.getResponse());

            return RequestResponseUtils.createSuccessResponse(responseBody, httpHeaders);
        } catch (Exception ex) {
            return RequestResponseUtils.createErrorResponse(
                    "Error processing images: " + ex.getMessage(), logger, "liveness detection");
        }
    }
}
