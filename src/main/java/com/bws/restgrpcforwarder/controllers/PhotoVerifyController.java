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
import java.util.Base64;
import com.bioid.services.Bws.PhotoVerifyRequest;
import com.bioid.services.Bwsmessages.ImageData;
import com.bws.restgrpcforwarder.datatypes.PhotoVerifyRequestJson;
import com.bws.restgrpcforwarder.grpc.GrpcClientService;
import com.bws.restgrpcforwarder.grpc.GrpcMetadataConverter;
import com.google.protobuf.ByteString;
import com.google.protobuf.util.JsonFormat;
import io.grpc.Metadata;

@RestController
@RequestMapping("photoverify")
public class PhotoVerifyController {

    private final GrpcClientService grpcClient;
    private static final Logger logger = LoggerFactory.getLogger(PhotoVerifyController.class);

    public PhotoVerifyController(GrpcClientService bwsGrpcClient) {
        this.grpcClient = bwsGrpcClient;
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<?>> onPost(@RequestHeader HttpHeaders headers, @RequestBody PhotoVerifyRequestJson photoVerifyRequest)
    {
        // In this example, the input images are encoded in base64strings.
        try
        {
            byte[] photo = new byte[0], image1 = new byte[0], image2 = new byte[0];

            // Extract the optional request header 'Reference-Number'.
            var referenceValue = headers.getFirst("Reference-Number");
            var referenceHeaderValue = (referenceValue == null )? "" : referenceValue;

            // Retrieve live images and id photo from rest request
            // Verify whether the id photo has been transmitted.
            if (!photoVerifyRequest.getPhoto().isEmpty())
            {
                // Convert image from base64string.
                photo = Base64.getDecoder().decode(photoVerifyRequest.getPhoto());
            }

            // Verify whether the first live image has been transmitted.
            if (photoVerifyRequest.getLiveImages().size() > 0)
            {
                // Convert image from base64string.
                image1 = Base64.getDecoder().decode(photoVerifyRequest.getLiveImages().get(0).getImage());
            }

            // Verify whether the second live image has been transmitted.
            if (photoVerifyRequest.getLiveImages().size() > 1)
            {
                // Convert image from base64string.
                image2 = Base64.getDecoder().decode(photoVerifyRequest.getLiveImages().get(1).getImage());
            }

            // Verify whether the request contains an id document and at least one live image.
            if (image1.length == 0 && photo.length == 0)
            {
                logger.error("Invalid parameter");
                return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("At least one ID photo and one live image must be submitted."));
            }

            // Convert image byte array in ImageData object.
            ImageData imageData1 = ImageData.newBuilder().setImage(ByteString.copyFrom(image1)).build();

            // Create PhotoVeriy request and add an id photo and a live image. 
            PhotoVerifyRequest verifyRequest = PhotoVerifyRequest.newBuilder()
            .setPhoto(ByteString.copyFrom(photo))
            .setDisableLivenessDetection(photoVerifyRequest.getDisableLivenessDetection())
            .addLiveImages(imageData1)
            .build();

            // If included, add a second live image to the photoverify request.
            if (image2.length > 0)
            {
                ImageData imageData2 = ImageData.newBuilder().setImage(ByteString.copyFrom(image2)).build();
                verifyRequest = verifyRequest.toBuilder().addLiveImages(imageData2).build();
            }
            // Add optional reference number header
            Metadata referenceHeader = new Metadata();
            Metadata.Key<String> customHeaderKey = Metadata.Key.of("Reference-Number", Metadata.ASCII_STRING_MARSHALLER);
            referenceHeader.put(customHeaderKey, referenceHeaderValue);

            // Call to bws photoverify api via grpc client.
            var call = grpcClient.photoVerifyAsync(verifyRequest, referenceHeader);
            // Read out the photoverify api response.
            var photoverifynResponse = call.get();

            logger.info("Call to photoVerify API returned "+ photoverifynResponse.getResponse().getStatus()+ " .");
            
            // Convert grpc metadata from the API response to
            // {@link org.springframework.http.HttpHeaders}.
            var httpresponseHeaders = GrpcMetadataConverter.convertMetadataToHttpHeaders(photoverifynResponse.getMetadata());
            var responseBody = JsonFormat.printer().print(photoverifynResponse.getResponse());

            return CompletableFuture.completedFuture(ResponseEntity.ok().headers(httpresponseHeaders).body(responseBody));

        } catch (Exception ex)
        {
            logger.error("An error has occurred:", ex);
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Error processing images: " + ex.getMessage()));
        }
    }
}
