package com.bws.restgrpcforwarder.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.CompletableFuture;
import com.bioid.services.Bws.VideoLivenessDetectionRequest;
import com.bws.restgrpcforwarder.datatypes.VideoLivenessDetectionRequestJson;
import com.bws.restgrpcforwarder.grpc.GrpcClientService;
import com.bws.restgrpcforwarder.grpc.GrpcMetadataConverter;
import com.bws.restgrpcforwarder.utils.RequestResponseUtils;
import com.google.protobuf.ByteString;
import com.google.protobuf.util.JsonFormat;
import java.util.Base64;
import io.grpc.Metadata;

/**
 * REST Controller for Video Liveness Detection operations
 * 
 * Provides endpoint for detecting liveness from video files
 * Processes video data encoded in Base64 format
 */
@RestController
@RequestMapping("videolivenessdetection")
public class VideoLivenessDetectionController {
    private final GrpcClientService grpcClient;
    private static final Logger logger = LoggerFactory.getLogger(LivenessDetectionController.class);

    public VideoLivenessDetectionController(GrpcClientService bwsGrpcClient) {
        grpcClient = bwsGrpcClient;
    }

    /**
     * Processes video liveness detection request
     * 
     * @param headers                       HTTP headers including optional
     *                                      Reference-Number
     * @param videoLivenessDetectionRequest JSON request with video data
     * @return CompletableFuture with video liveness detection result
     */
    @PostMapping()
    public CompletableFuture<ResponseEntity<?>> onPost(@RequestHeader HttpHeaders headers,
            @RequestBody VideoLivenessDetectionRequestJson videoLivenessDetectionRequest) {
        try {
            // Extract reference number using utility method
            String referenceNumber = RequestResponseUtils.extractReferenceNumber(headers);

            // In this example, the input images are encoded in base64strings.
            // Decode video file from Base64
            byte[] video = RequestResponseUtils.safeBase64Decode(
                    videoLivenessDetectionRequest.getVideo(), "video file");

            // Validate that video data is present
            if (video.length == 0) {
                return RequestResponseUtils.createErrorResponse(
                        "No video file provided", logger, "video liveness detection");
            }

             // Create VideoLivenessDetection request with video data
            VideoLivenessDetectionRequest videoRequest = VideoLivenessDetectionRequest.newBuilder()
                .setVideo(ByteString.copyFrom(video))
                .build();

            // Create gRPC metadata with reference number
            Metadata referenceHeader = RequestResponseUtils.createReferenceHeader(referenceNumber);

            // Execute gRPC call
            var call = grpcClient.videoLivenessDetectionAsync(videoRequest, referenceHeader);
            var detectionResponse = call.get();

            logger.info("Video liveness detection API returned status: {}", 
                detectionResponse.getResponse().getStatus());

            // Convert and return response
            var httpHeaders = GrpcMetadataConverter.convertMetadataToHttpHeaders(detectionResponse.getMetadata());
            var responseBody = JsonFormat.printer().print(detectionResponse.getResponse());

            return RequestResponseUtils.createSuccessResponse(responseBody, httpHeaders);

        } catch (Exception ex) {
            return RequestResponseUtils.createErrorResponse(
                "Error processing video: " + ex.getMessage(), logger, "video liveness detection");
        }
    }
}
