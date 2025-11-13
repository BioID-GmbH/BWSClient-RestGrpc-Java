package com.bws.restgrpcforwarder.grpc;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.bioid.services.BioIDWebServiceGrpc;
import com.bioid.services.Bws.LivenessDetectionRequest;
import com.bioid.services.Bws.LivenessDetectionResponse;
import com.bioid.services.Bws.PhotoVerifyRequest;
import com.bioid.services.Bws.PhotoVerifyResponse;
import com.bioid.services.Bws.VideoLivenessDetectionRequest;
import com.bws.restgrpcforwarder.config.GrpcClientConfig;
import com.bws.restgrpcforwarder.datatypes.LivenessDetectionResult;
import com.bws.restgrpcforwarder.datatypes.PhotoVerifyResult;
import com.bws.restgrpcforwarder.security.JwtCallCredetials;
import com.bws.restgrpcforwarder.security.JwtTokenProvider;
import io.grpc.CallCredentials;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

/**
 * gRPC Client Service for BioID Web Services
 * 
 * This service provides thread-safe asynchronous operations for:
 * - Liveness Detection
 * - Photo Verification
 * - Video Liveness Detection
 * 
 * Features:
 * - JWT authentication with call credentials
 * - Thread-safe stub creation per request
 * - Async processing with CompletableFuture
 * - Proper connection management and cleanup
 * - Header propagation for correlation IDs
 */
@Service
public class GrpcClientService {

    private ManagedChannel channel;
    private final CallCredentials jwtCallCredentials;
    private static final Logger logger = LoggerFactory.getLogger(GrpcClientService.class);

    /**
     * Constructs a GrpcClientService with the provided configuration
     * 
     * @param appConfig the gRPC client configuration containing endpoint,
     *                  credentials, and JWT settings
     * @throws RuntimeException if gRPC client initialization fails
     */
    public GrpcClientService(GrpcClientConfig appConfig) {
        try {
            // Generate JWT token for authentication
            String jwtToken = JwtTokenProvider.generateToken(
                    appConfig.getClientId(),
                    appConfig.getAccessKey(),
                    appConfig.getAudience(),
                    appConfig.getExpirationInMinutes());

            // Create managed channel with transport security
            channel = ManagedChannelBuilder.forTarget(appConfig.getEndpoint()).useTransportSecurity().build();
            jwtCallCredentials = new JwtCallCredetials(jwtToken);

        } catch (Exception e) {
            logger.error("An error has occurred during gRPC client initialization:", e);
            throw new RuntimeException("Failed to initialize GrpcClientService", e);
        }
    }

    /**
     * Performs asynchronous liveness detection
     * 
     * @param livenessRequest The liveness detection request with image data
     * @param headers         HTTP headers to forward (e.g., Reference-Number)
     * @return CompletableFuture containing detection result with metadata
     */
    @Async
    public CompletableFuture<LivenessDetectionResult> livenessDetectionAsync(LivenessDetectionRequest livenessRequest,
            Metadata headers) {
        try {
            AtomicReference<Metadata> responseHeaders = new AtomicReference<>();
            AtomicReference<Metadata> responseTrailers = new AtomicReference<>();

            // Create new stub instance for each call with required interceptors
            var stub = BioIDWebServiceGrpc.newStub(channel)
                    .withCallCredentials(jwtCallCredentials)
                    .withInterceptors(
                            new HeaderClientInterceptor(headers),
                            MetadataUtils.newCaptureMetadataInterceptor(responseHeaders, responseTrailers));

            CompletableFuture<LivenessDetectionResult> livenessResult = new CompletableFuture<>();
            stub.livenessDetection(livenessRequest, new StreamObserver<LivenessDetectionResponse>() {
                @Override
                public void onNext(LivenessDetectionResponse value) {
                    var apiResponse = new LivenessDetectionResult(value, responseHeaders.get());
                    livenessResult.complete(apiResponse);
                }

                @Override
                public void onError(Throwable t) {
                    livenessResult.completeExceptionally(t);
                }

                @Override
                public void onCompleted() {
                }
            });
            return livenessResult;
        } catch (Exception e) {
            logger.error("An error has occurred:", e);
            return CompletableFuture.failedFuture(e);
        }
    }

    /**
     * Performs asynchronous photo verification
     * 
     * @param photoverifyRequest The photo verification request with ID photo and live images
     * @param headers            HTTP headers to forward (e.g., Reference-Number)
     * @return CompletableFuture containing verification result with metadata
     */
    @Async
    public CompletableFuture<PhotoVerifyResult> photoVerifyAsync(PhotoVerifyRequest photoverifyRequest,
            Metadata headers) {
        try {
            AtomicReference<Metadata> responseHeaders = new AtomicReference<>();
            AtomicReference<Metadata> responseTrailers = new AtomicReference<>();

            // Create new stub instance for each call
            var stub = BioIDWebServiceGrpc.newStub(channel)
                    .withCallCredentials(jwtCallCredentials)
                    .withInterceptors(
                            new HeaderClientInterceptor(headers),
                            MetadataUtils.newCaptureMetadataInterceptor(responseHeaders, responseTrailers));

            CompletableFuture<PhotoVerifyResult> photoVerifyResult = new CompletableFuture<>();
            stub.photoVerify(photoverifyRequest, new StreamObserver<PhotoVerifyResponse>() {
                @Override
                public void onNext(PhotoVerifyResponse value) {
                    var apiResponse = new PhotoVerifyResult(value, responseHeaders.get());
                    photoVerifyResult.complete(apiResponse);
                }

                @Override
                public void onError(Throwable t) {
                    photoVerifyResult.completeExceptionally(t);
                }

                @Override
                public void onCompleted() {
                }
            });
            return photoVerifyResult;
        } catch (Exception e) {
            logger.error("An error has occurred:", e);
            return CompletableFuture.failedFuture(e);
        }
    }

    /**
     * Performs asynchronous video liveness detection
     * 
     * @param videoLivenessRequest The video liveness detection request with video data
     * @param headers HTTP headers to forward (e.g., Reference-Number)
     * @return CompletableFuture containing detection result with metadata
     */
    @Async
    public CompletableFuture<LivenessDetectionResult> videoLivenessDetectionAsync(
            VideoLivenessDetectionRequest videoLivenessRequest, Metadata headers) {
        try {
            AtomicReference<Metadata> responseHeaders = new AtomicReference<>();
            AtomicReference<Metadata> responseTrailers = new AtomicReference<>();

            var stub = BioIDWebServiceGrpc.newStub(channel)
                    .withCallCredentials(jwtCallCredentials)
                    .withInterceptors(
                            new HeaderClientInterceptor(headers),
                            MetadataUtils.newCaptureMetadataInterceptor(responseHeaders, responseTrailers));

            CompletableFuture<LivenessDetectionResult> videoLivenessResult = new CompletableFuture<>();
            stub.videoLivenessDetection(videoLivenessRequest,
                    new StreamObserver<LivenessDetectionResponse>() {
                        @Override
                        public void onNext(LivenessDetectionResponse value) {
                            var apiResponse = new LivenessDetectionResult(value, responseHeaders.get());
                            videoLivenessResult.complete(apiResponse);
                        }

                        @Override
                        public void onError(Throwable t) {
                            videoLivenessResult.completeExceptionally(t);
                        }

                        @Override
                        public void onCompleted() {
                        }
                    });
            return videoLivenessResult;
        } catch (Exception e) {
            logger.error("An error has occurred:", e);
            return CompletableFuture.failedFuture(e);
        }
    }

    /**
     * Shuts down the gRPC channel gracefully
     * This method is called automatically when the application context is destroyed
     */
    @PreDestroy
    public void shutdownChannel() {
        channel.shutdown();
    }
}