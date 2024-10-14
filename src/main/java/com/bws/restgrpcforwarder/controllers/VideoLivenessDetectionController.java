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
import com.bioid.services.Bws.ImageData;
import com.bioid.services.Bws.LivenessDetectionRequest;
import com.bioid.services.Bws.PhotoVerifyRequest;
import com.bioid.services.Bws.VideoLivenessDetectionRequest;
import com.bws.restgrpcforwarder.datatypes.LivenessDetectionRequestJson;
import com.bws.restgrpcforwarder.datatypes.VideoLivenessDetectionRequestJson;
import com.bws.restgrpcforwarder.grpc.GrpcClientService;
import com.bws.restgrpcforwarder.grpc.GrpcMetadataConverter;
import com.google.protobuf.ByteString;
import com.google.protobuf.util.JsonFormat;
import java.util.Base64;
import io.grpc.Metadata;

@RestController
@RequestMapping("videolivenessdetection")
public class VideoLivenessDetectionController {
    private final GrpcClientService grpcClient;
    private static final Logger logger = LoggerFactory.getLogger(LivenessDetectionController.class);

    public VideoLivenessDetectionController(GrpcClientService bwsGrpcClient) {
        grpcClient = bwsGrpcClient;
    }

    @PostMapping()
    public CompletableFuture<ResponseEntity<?>> onPost(@RequestHeader HttpHeaders headers,
            @RequestBody VideoLivenessDetectionRequestJson videoLivenessDetectionRequest)
    {
        // In this example, the input images are encoded in base64strings.
        try
        {
            byte[] video = new byte[0];

            // Extract the optional request header 'Reference-Number'.
            var referenceHeaderValue = headers.getFirst("referencenumber");

            // Extract video file from request.
            if (!videoLivenessDetectionRequest.getVideo().isEmpty())
            {
                // Convert video from base64string.
                video = Base64.getDecoder().decode(videoLivenessDetectionRequest.getVideo());
            }

            // Verify if the request includes a video file.
            if (video.length == 0)
            {
                logger.error("No video file provided.");
                return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("No video file provided."));
            }

            // Create VideoLIvenessDetection request and add an video file. 
            VideoLivenessDetectionRequest videoRequest = VideoLivenessDetectionRequest.newBuilder()
            .setVideo(ByteString.copyFrom(video))
            .build();

            // Add optional reference number header
            Metadata referenceHeader = new Metadata();
            Metadata.Key<String> customHeaderKey = Metadata.Key.of("Reference-Number", Metadata.ASCII_STRING_MARSHALLER);
            referenceHeader.put(customHeaderKey, referenceHeaderValue);

            // Call to bws videolivenessdetetction api via grpc client.
            var call = grpcClient.videoLivenessDetectionAsync(videoRequest, referenceHeader);
            // Read out the videolivenessdetection api response.
            var videoLivenessDetecionResponse = call.get();

            logger.info("Call to videoLivedetection API returned "+ videoLivenessDetecionResponse.getResponse().getStatus()+".");
            
            // Convert grpc metadata from the API response to
            // {@link org.springframework.http.HttpHeaders}.
            var httpresponseHeaders = GrpcMetadataConverter.convertMetadataToHttpHeaders(videoLivenessDetecionResponse.getMetadata());
            var responseBody = JsonFormat.printer().print(videoLivenessDetecionResponse.getResponse());

            return CompletableFuture.completedFuture(ResponseEntity.ok().headers(httpresponseHeaders).body(responseBody));

        } catch (Exception ex)
        {
            logger.error("An error has occurred:", ex);
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Error processing images: " + ex.getMessage()));
        }
    }
}
