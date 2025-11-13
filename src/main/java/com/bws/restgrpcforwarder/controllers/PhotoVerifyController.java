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
import com.bioid.services.Bws.PhotoVerifyRequest;
import com.bioid.services.Bwsmessages.ImageData;
import com.bws.restgrpcforwarder.datatypes.PhotoVerifyRequestJson;
import com.bws.restgrpcforwarder.grpc.GrpcClientService;
import com.bws.restgrpcforwarder.grpc.GrpcMetadataConverter;
import com.bws.restgrpcforwarder.utils.RequestResponseUtils;
import com.google.protobuf.ByteString;
import com.google.protobuf.util.JsonFormat;
import io.grpc.Metadata;

/**
 * REST Controller for Photo Verification operations
 * 
 * Provides endpoint for verifying ID photos against live images
 * Supports liveness detection enable/disable
 */
@RestController
@RequestMapping("photoverify")
public class PhotoVerifyController {

    private final GrpcClientService grpcClient;
    private static final Logger logger = LoggerFactory.getLogger(PhotoVerifyController.class);

    public PhotoVerifyController(GrpcClientService bwsGrpcClient) {
        this.grpcClient = bwsGrpcClient;
    }

    /**
     * Processes photo verification request
     * 
     * @param headers HTTP headers including optional Reference-Number
     * @param photoVerifyRequest JSON request with ID photo and live images
     * @return CompletableFuture with photo verification result
     */
    @PostMapping
    public CompletableFuture<ResponseEntity<?>> onPost(@RequestHeader HttpHeaders headers,
            @RequestBody PhotoVerifyRequestJson photoVerifyRequest) {
        try {
            // Extract reference number
            String referenceNumber = RequestResponseUtils.extractReferenceNumber(headers);

            // In this example, the input images are encoded in base64strings.
            // Decode ID photo
            byte[] photo = RequestResponseUtils.safeBase64Decode(photoVerifyRequest.getPhoto(), "ID photo");

            // Decode live images
            byte[] image1 = new byte[0];
            byte[] image2 = new byte[0];

            if (!photoVerifyRequest.getLiveImages().isEmpty()) {
                image1 = RequestResponseUtils.safeBase64Decode(
                        photoVerifyRequest.getLiveImages().get(0).getImage(), "first live image");
            }

            if (photoVerifyRequest.getLiveImages().size() > 1) {
                image2 = RequestResponseUtils.safeBase64Decode(
                        photoVerifyRequest.getLiveImages().get(1).getImage(), "second live image");
            }

            // Validate that we have at least one ID photo or live image
            if (image1.length == 0 && photo.length == 0) {
                return RequestResponseUtils.createErrorResponse(
                        "At least one ID photo and one live image must be submitted",
                        logger, "photo verification");
            }

            // Build photo verify request
            PhotoVerifyRequest.Builder requestBuilder = PhotoVerifyRequest.newBuilder()
                    .setPhoto(ByteString.copyFrom(photo))
                    .setDisableLivenessDetection(photoVerifyRequest.getDisableLivenessDetection())
                    .addLiveImages(ImageData.newBuilder().setImage(ByteString.copyFrom(image1)).build());

            // Add second live image if present
            if (image2.length > 0) {
                requestBuilder.addLiveImages(ImageData.newBuilder().setImage(ByteString.copyFrom(image2)).build());
            }

            // Create metadata and execute gRPC call
            Metadata referenceHeader = RequestResponseUtils.createReferenceHeader(referenceNumber);
            var call = grpcClient.photoVerifyAsync(requestBuilder.build(), referenceHeader);
            var verificationResponse = call.get();

            logger.info("Photo verify API returned status: {}",
                    verificationResponse.getResponse().getStatus());

            // Convert and return response
            var httpHeaders = GrpcMetadataConverter.convertMetadataToHttpHeaders(verificationResponse.getMetadata());
            var responseBody = JsonFormat.printer().print(verificationResponse.getResponse());

            return RequestResponseUtils.createSuccessResponse(responseBody, httpHeaders);

        } catch (Exception ex) {
            return RequestResponseUtils.createErrorResponse(
                    "Error processing images: " + ex.getMessage(), logger, "photo verification");
        }
    }
}
