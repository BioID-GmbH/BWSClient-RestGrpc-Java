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
 * Service class for gRPC client operations.
 * This class provides operation methods for the different BWS APIs.
 */
@Service
public class GrpcClientService {

    private BioIDWebServiceGrpc.BioIDWebServiceStub bwsClientAsync;

    private ManagedChannel channel;
    private static final Logger logger = LoggerFactory.getLogger(GrpcClientService.class);

    /**
     * Constructs a GrpcClientService with the provided configuration.
     *
     * @param appConfig the gRPC client configuration
     */
    public GrpcClientService(GrpcClientConfig appConfig) {
        try {
            String jwtToken = JwtTokenProvider.generateToken(appConfig.clientId, appConfig.accessKey,
                    appConfig.audience,
                    appConfig.tokenExpirationTime);

            channel = ManagedChannelBuilder.forTarget(appConfig.serviceEndpoint).useTransportSecurity().build();
            CallCredentials jwtCallCredentials = new JwtCallCredetials(jwtToken);
            bwsClientAsync = BioIDWebServiceGrpc.newStub(channel).withCallCredentials(jwtCallCredentials);

        } catch (Exception e) {
            logger.error("An error has occurred:", e);
            e.printStackTrace();
        }
    }

    /**
     * Performs an asynchronous livenessDetection.
     *
     * @param livenessRequest The livenessdetection request.
     * @param headers         The http metadata headers.
     * @return a CompletableFuture containing the livenessdetection api result with
     *         grpc response metadata.
     */
    @Async
    public CompletableFuture<LivenessDetectionResult> livenessDetectionAsync(LivenessDetectionRequest livenessRequest,
            Metadata headers) {
        try {
            AtomicReference<Metadata> responseHeaders = new AtomicReference<>();
            AtomicReference<Metadata> responseTrailers = new AtomicReference<>();

            bwsClientAsync = bwsClientAsync.withInterceptors(new HeaderClientInterceptor(headers),
                    MetadataUtils.newCaptureMetadataInterceptor(responseHeaders,
                            responseTrailers));

            CompletableFuture<LivenessDetectionResult> livenessResult = new CompletableFuture<>();
            bwsClientAsync.livenessDetection(livenessRequest, new StreamObserver<LivenessDetectionResponse>() {
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
     * Performs an asynchronous photoverify.
     *
     * @param photoverifyRequest The photoverify request.
     * @param headers            The http metadata headers.
     * @return a CompletableFuture containing the photoverify api result with grpc
     *         response metadata.
     */
    @Async
    public CompletableFuture<PhotoVerifyResult> photoVerifyAsync(PhotoVerifyRequest photoverifyRequest,
            Metadata headers) {
        try {
            AtomicReference<Metadata> responseHeaders = new AtomicReference<>();
            AtomicReference<Metadata> responseTrailers = new AtomicReference<>();

            // Add an additional header to the grpc request.
            bwsClientAsync = bwsClientAsync.withInterceptors(new HeaderClientInterceptor(headers),
                    MetadataUtils.newCaptureMetadataInterceptor(responseHeaders, responseTrailers));

            CompletableFuture<PhotoVerifyResult> photoVerifyResult = new CompletableFuture<>();

            bwsClientAsync.photoVerify(photoverifyRequest, new StreamObserver<PhotoVerifyResponse>() {
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
     * Performs an asynchronous videolivenessDetection.
     *
     * @param livenessRequest The videolivenessdetection request.
     * @param headers         The http metadata headers.
     * @return a CompletableFuture containing the videolivenessdetection api result
     *         with grpc response metadata.
     */
    @Async
    public CompletableFuture<LivenessDetectionResult> videoLivenessDetectionAsync(
            VideoLivenessDetectionRequest videoLivenessRequest, Metadata headers) {
        try {
            AtomicReference<Metadata> responseHeaders = new AtomicReference<>();
            AtomicReference<Metadata> responseTrailers = new AtomicReference<>();

            // Add an additional header to the grpc request.
            bwsClientAsync = bwsClientAsync.withInterceptors(new HeaderClientInterceptor(headers),
                    MetadataUtils.newCaptureMetadataInterceptor(responseHeaders, responseTrailers));

            CompletableFuture<LivenessDetectionResult> videoLivenessResult = new CompletableFuture<>();

            bwsClientAsync.videoLivenessDetection(videoLivenessRequest,
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
     * Shuts down the gRPC channel.
     */
    @PreDestroy
    public void shutdownChannel() {
        channel.shutdown();
    }
}