package com.bws.restgrpcforwarder.controllers;

import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bioid.services.Bws.ImageData;
import com.bioid.services.Bws.LivenessDetectionRequest;
import com.bws.restgrpcforwarder.datatypes.LivenessDetectionRequestJson;
import com.bws.restgrpcforwarder.grpc.GrpcClientService;
import com.bws.restgrpcforwarder.grpc.GrpcMetadataConverter;
import com.google.protobuf.ByteString;
import com.google.protobuf.util.JsonFormat;
import io.grpc.Metadata;

@RestController
@RequestMapping("livenessdetection")
public class LivenessDetectionController {

    private final GrpcClientService grpcClient;
    private static final Logger logger = LoggerFactory.getLogger(LivenessDetectionController.class);

    public LivenessDetectionController(GrpcClientService bwsGrpcClient) {
        grpcClient = bwsGrpcClient;
    }

    @GetMapping
    public ResponseEntity<String> onGet()
    {
        logger.info("passsttsts");
        return ResponseEntity.ok("Test rest get Request");
    }

    @PostMapping()
    public CompletableFuture<ResponseEntity<?>> onPost(@RequestHeader HttpHeaders headers,
            @RequestBody LivenessDetectionRequestJson livenessDetectionRequest)
    {
        // In this example, the input images are encoded in base64strings.
        try
        {
            byte[] image1 = new byte[0], image2 = new byte[0];
            // Extract the optional request header 'Reference-Number'.
            var referenceHeaderValue = headers.getFirst("Reference-Number");

            // Verify whether the first live image has been transmitted.
            if (livenessDetectionRequest.getLiveImages().size() > 0)
            {
                // Convert image from base64string.
                image1 = Base64.getDecoder().decode(livenessDetectionRequest.getLiveImages().get(0).getImage());
            } else
            {
                logger.error("ANo live images transmitted.");
                return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("No live images transmitted."));
            }

            // Verify whether the second live image has been transmitted.
            if (livenessDetectionRequest.getLiveImages().size() > 1)
            {
                // Convert image from base64string.
                image2 = Base64.getDecoder().decode(livenessDetectionRequest.getLiveImages().get(1).getImage());
            }
            // Convert image byte array in ImageData object.
            ImageData imageData1 = ImageData.newBuilder().setImage(ByteString.copyFrom(image1)).build();
            
            // Create livenessDetection request.
            LivenessDetectionRequest livenessRequest = LivenessDetectionRequest.newBuilder().addLiveImages(imageData1).build();
            if (image2.length > 0)
            {
                ImageData imageData2 = ImageData.newBuilder().setImage(ByteString.copyFrom(image2)).build();
                String secondImageTag = "";
                if(!livenessDetectionRequest.getLiveImages().get(1).getTags().isEmpty())
                {
                    secondImageTag = livenessDetectionRequest.getLiveImages().get(1).getTags().getFirst();
                }
                if (!secondImageTag.isEmpty())
                {
                    // Add a tag to the request for challenge response
                    imageData2 = imageData2.toBuilder().addTags(secondImageTag).build();
                }
                livenessRequest = livenessRequest.toBuilder().addLiveImages(imageData2).build();
            }

            // Add optional reference number header
            Metadata referenceHeader = new Metadata();
            Metadata.Key<String> customHeaderKey = Metadata.Key.of("Reference-Number", Metadata.ASCII_STRING_MARSHALLER);
            referenceHeader.put(customHeaderKey, referenceHeaderValue);

            // Call to bws livenessdetetction api via grpc client.
            var call = grpcClient.livenessDetectionAsync(livenessRequest, referenceHeader);
            // Read out the livenessdetection api response.
            var livenessDetecionResponse = call.get();

            logger.info("Call to livedetection API returned " + livenessDetecionResponse.getResponse().getStatus() + " .");

            // Convert grpc metadata from the API response to
            // {@link org.springframework.http.HttpHeaders}.
            var httpresponseHeaders = GrpcMetadataConverter.convertMetadataToHttpHeaders(livenessDetecionResponse.getMetadata());
            var responseBody = JsonFormat.printer().print(livenessDetecionResponse.getResponse());

            return CompletableFuture.completedFuture(ResponseEntity.ok().headers(httpresponseHeaders).body(responseBody));

        } catch (Exception ex)
        {
            logger.error("An error has occurred:", ex);
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Error processing images: " + ex.getMessage()));
        }
    }

}
