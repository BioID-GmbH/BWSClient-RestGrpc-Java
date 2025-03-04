package com.bioid.services;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * BioID Web Service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.70.0)",
    comments = "Source: bws.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class BioIDWebServiceGrpc {

  private BioIDWebServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "bioid.services.v1.BioIDWebService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.bioid.services.Bws.LivenessDetectionRequest,
      com.bioid.services.Bws.LivenessDetectionResponse> getLivenessDetectionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "LivenessDetection",
      requestType = com.bioid.services.Bws.LivenessDetectionRequest.class,
      responseType = com.bioid.services.Bws.LivenessDetectionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.bioid.services.Bws.LivenessDetectionRequest,
      com.bioid.services.Bws.LivenessDetectionResponse> getLivenessDetectionMethod() {
    io.grpc.MethodDescriptor<com.bioid.services.Bws.LivenessDetectionRequest, com.bioid.services.Bws.LivenessDetectionResponse> getLivenessDetectionMethod;
    if ((getLivenessDetectionMethod = BioIDWebServiceGrpc.getLivenessDetectionMethod) == null) {
      synchronized (BioIDWebServiceGrpc.class) {
        if ((getLivenessDetectionMethod = BioIDWebServiceGrpc.getLivenessDetectionMethod) == null) {
          BioIDWebServiceGrpc.getLivenessDetectionMethod = getLivenessDetectionMethod =
              io.grpc.MethodDescriptor.<com.bioid.services.Bws.LivenessDetectionRequest, com.bioid.services.Bws.LivenessDetectionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "LivenessDetection"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.bioid.services.Bws.LivenessDetectionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.bioid.services.Bws.LivenessDetectionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BioIDWebServiceMethodDescriptorSupplier("LivenessDetection"))
              .build();
        }
      }
    }
    return getLivenessDetectionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.bioid.services.Bws.VideoLivenessDetectionRequest,
      com.bioid.services.Bws.LivenessDetectionResponse> getVideoLivenessDetectionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "VideoLivenessDetection",
      requestType = com.bioid.services.Bws.VideoLivenessDetectionRequest.class,
      responseType = com.bioid.services.Bws.LivenessDetectionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.bioid.services.Bws.VideoLivenessDetectionRequest,
      com.bioid.services.Bws.LivenessDetectionResponse> getVideoLivenessDetectionMethod() {
    io.grpc.MethodDescriptor<com.bioid.services.Bws.VideoLivenessDetectionRequest, com.bioid.services.Bws.LivenessDetectionResponse> getVideoLivenessDetectionMethod;
    if ((getVideoLivenessDetectionMethod = BioIDWebServiceGrpc.getVideoLivenessDetectionMethod) == null) {
      synchronized (BioIDWebServiceGrpc.class) {
        if ((getVideoLivenessDetectionMethod = BioIDWebServiceGrpc.getVideoLivenessDetectionMethod) == null) {
          BioIDWebServiceGrpc.getVideoLivenessDetectionMethod = getVideoLivenessDetectionMethod =
              io.grpc.MethodDescriptor.<com.bioid.services.Bws.VideoLivenessDetectionRequest, com.bioid.services.Bws.LivenessDetectionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "VideoLivenessDetection"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.bioid.services.Bws.VideoLivenessDetectionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.bioid.services.Bws.LivenessDetectionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BioIDWebServiceMethodDescriptorSupplier("VideoLivenessDetection"))
              .build();
        }
      }
    }
    return getVideoLivenessDetectionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.bioid.services.Bws.PhotoVerifyRequest,
      com.bioid.services.Bws.PhotoVerifyResponse> getPhotoVerifyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PhotoVerify",
      requestType = com.bioid.services.Bws.PhotoVerifyRequest.class,
      responseType = com.bioid.services.Bws.PhotoVerifyResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.bioid.services.Bws.PhotoVerifyRequest,
      com.bioid.services.Bws.PhotoVerifyResponse> getPhotoVerifyMethod() {
    io.grpc.MethodDescriptor<com.bioid.services.Bws.PhotoVerifyRequest, com.bioid.services.Bws.PhotoVerifyResponse> getPhotoVerifyMethod;
    if ((getPhotoVerifyMethod = BioIDWebServiceGrpc.getPhotoVerifyMethod) == null) {
      synchronized (BioIDWebServiceGrpc.class) {
        if ((getPhotoVerifyMethod = BioIDWebServiceGrpc.getPhotoVerifyMethod) == null) {
          BioIDWebServiceGrpc.getPhotoVerifyMethod = getPhotoVerifyMethod =
              io.grpc.MethodDescriptor.<com.bioid.services.Bws.PhotoVerifyRequest, com.bioid.services.Bws.PhotoVerifyResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PhotoVerify"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.bioid.services.Bws.PhotoVerifyRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.bioid.services.Bws.PhotoVerifyResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BioIDWebServiceMethodDescriptorSupplier("PhotoVerify"))
              .build();
        }
      }
    }
    return getPhotoVerifyMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static BioIDWebServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BioIDWebServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BioIDWebServiceStub>() {
        @java.lang.Override
        public BioIDWebServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BioIDWebServiceStub(channel, callOptions);
        }
      };
    return BioIDWebServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static BioIDWebServiceBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BioIDWebServiceBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BioIDWebServiceBlockingV2Stub>() {
        @java.lang.Override
        public BioIDWebServiceBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BioIDWebServiceBlockingV2Stub(channel, callOptions);
        }
      };
    return BioIDWebServiceBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static BioIDWebServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BioIDWebServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BioIDWebServiceBlockingStub>() {
        @java.lang.Override
        public BioIDWebServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BioIDWebServiceBlockingStub(channel, callOptions);
        }
      };
    return BioIDWebServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static BioIDWebServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BioIDWebServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BioIDWebServiceFutureStub>() {
        @java.lang.Override
        public BioIDWebServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BioIDWebServiceFutureStub(channel, callOptions);
        }
      };
    return BioIDWebServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * BioID Web Service definition.
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * Liveness-detection API
     * - 1 image: passive liveness detection only
     * - 2 images: passive and active liveness detection
     * - 2 images and tags: active liveness detection with challenge response
     * </pre>
     */
    default void livenessDetection(com.bioid.services.Bws.LivenessDetectionRequest request,
        io.grpc.stub.StreamObserver<com.bioid.services.Bws.LivenessDetectionResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLivenessDetectionMethod(), responseObserver);
    }

    /**
     * <pre>
     * Video liveness-detection API
     * </pre>
     */
    default void videoLivenessDetection(com.bioid.services.Bws.VideoLivenessDetectionRequest request,
        io.grpc.stub.StreamObserver<com.bioid.services.Bws.LivenessDetectionResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getVideoLivenessDetectionMethod(), responseObserver);
    }

    /**
     * <pre>
     * Photo-verification API
     * </pre>
     */
    default void photoVerify(com.bioid.services.Bws.PhotoVerifyRequest request,
        io.grpc.stub.StreamObserver<com.bioid.services.Bws.PhotoVerifyResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getPhotoVerifyMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service BioIDWebService.
   * <pre>
   * BioID Web Service definition.
   * </pre>
   */
  public static abstract class BioIDWebServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return BioIDWebServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service BioIDWebService.
   * <pre>
   * BioID Web Service definition.
   * </pre>
   */
  public static final class BioIDWebServiceStub
      extends io.grpc.stub.AbstractAsyncStub<BioIDWebServiceStub> {
    private BioIDWebServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BioIDWebServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BioIDWebServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     * Liveness-detection API
     * - 1 image: passive liveness detection only
     * - 2 images: passive and active liveness detection
     * - 2 images and tags: active liveness detection with challenge response
     * </pre>
     */
    public void livenessDetection(com.bioid.services.Bws.LivenessDetectionRequest request,
        io.grpc.stub.StreamObserver<com.bioid.services.Bws.LivenessDetectionResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLivenessDetectionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Video liveness-detection API
     * </pre>
     */
    public void videoLivenessDetection(com.bioid.services.Bws.VideoLivenessDetectionRequest request,
        io.grpc.stub.StreamObserver<com.bioid.services.Bws.LivenessDetectionResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getVideoLivenessDetectionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Photo-verification API
     * </pre>
     */
    public void photoVerify(com.bioid.services.Bws.PhotoVerifyRequest request,
        io.grpc.stub.StreamObserver<com.bioid.services.Bws.PhotoVerifyResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getPhotoVerifyMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service BioIDWebService.
   * <pre>
   * BioID Web Service definition.
   * </pre>
   */
  public static final class BioIDWebServiceBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<BioIDWebServiceBlockingV2Stub> {
    private BioIDWebServiceBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BioIDWebServiceBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BioIDWebServiceBlockingV2Stub(channel, callOptions);
    }

    /**
     * <pre>
     * Liveness-detection API
     * - 1 image: passive liveness detection only
     * - 2 images: passive and active liveness detection
     * - 2 images and tags: active liveness detection with challenge response
     * </pre>
     */
    public com.bioid.services.Bws.LivenessDetectionResponse livenessDetection(com.bioid.services.Bws.LivenessDetectionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLivenessDetectionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Video liveness-detection API
     * </pre>
     */
    public com.bioid.services.Bws.LivenessDetectionResponse videoLivenessDetection(com.bioid.services.Bws.VideoLivenessDetectionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getVideoLivenessDetectionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Photo-verification API
     * </pre>
     */
    public com.bioid.services.Bws.PhotoVerifyResponse photoVerify(com.bioid.services.Bws.PhotoVerifyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPhotoVerifyMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service BioIDWebService.
   * <pre>
   * BioID Web Service definition.
   * </pre>
   */
  public static final class BioIDWebServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<BioIDWebServiceBlockingStub> {
    private BioIDWebServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BioIDWebServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BioIDWebServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Liveness-detection API
     * - 1 image: passive liveness detection only
     * - 2 images: passive and active liveness detection
     * - 2 images and tags: active liveness detection with challenge response
     * </pre>
     */
    public com.bioid.services.Bws.LivenessDetectionResponse livenessDetection(com.bioid.services.Bws.LivenessDetectionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLivenessDetectionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Video liveness-detection API
     * </pre>
     */
    public com.bioid.services.Bws.LivenessDetectionResponse videoLivenessDetection(com.bioid.services.Bws.VideoLivenessDetectionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getVideoLivenessDetectionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Photo-verification API
     * </pre>
     */
    public com.bioid.services.Bws.PhotoVerifyResponse photoVerify(com.bioid.services.Bws.PhotoVerifyRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getPhotoVerifyMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service BioIDWebService.
   * <pre>
   * BioID Web Service definition.
   * </pre>
   */
  public static final class BioIDWebServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<BioIDWebServiceFutureStub> {
    private BioIDWebServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BioIDWebServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BioIDWebServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Liveness-detection API
     * - 1 image: passive liveness detection only
     * - 2 images: passive and active liveness detection
     * - 2 images and tags: active liveness detection with challenge response
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.bioid.services.Bws.LivenessDetectionResponse> livenessDetection(
        com.bioid.services.Bws.LivenessDetectionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLivenessDetectionMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Video liveness-detection API
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.bioid.services.Bws.LivenessDetectionResponse> videoLivenessDetection(
        com.bioid.services.Bws.VideoLivenessDetectionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getVideoLivenessDetectionMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Photo-verification API
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<com.bioid.services.Bws.PhotoVerifyResponse> photoVerify(
        com.bioid.services.Bws.PhotoVerifyRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getPhotoVerifyMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LIVENESS_DETECTION = 0;
  private static final int METHODID_VIDEO_LIVENESS_DETECTION = 1;
  private static final int METHODID_PHOTO_VERIFY = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LIVENESS_DETECTION:
          serviceImpl.livenessDetection((com.bioid.services.Bws.LivenessDetectionRequest) request,
              (io.grpc.stub.StreamObserver<com.bioid.services.Bws.LivenessDetectionResponse>) responseObserver);
          break;
        case METHODID_VIDEO_LIVENESS_DETECTION:
          serviceImpl.videoLivenessDetection((com.bioid.services.Bws.VideoLivenessDetectionRequest) request,
              (io.grpc.stub.StreamObserver<com.bioid.services.Bws.LivenessDetectionResponse>) responseObserver);
          break;
        case METHODID_PHOTO_VERIFY:
          serviceImpl.photoVerify((com.bioid.services.Bws.PhotoVerifyRequest) request,
              (io.grpc.stub.StreamObserver<com.bioid.services.Bws.PhotoVerifyResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getLivenessDetectionMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.bioid.services.Bws.LivenessDetectionRequest,
              com.bioid.services.Bws.LivenessDetectionResponse>(
                service, METHODID_LIVENESS_DETECTION)))
        .addMethod(
          getVideoLivenessDetectionMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.bioid.services.Bws.VideoLivenessDetectionRequest,
              com.bioid.services.Bws.LivenessDetectionResponse>(
                service, METHODID_VIDEO_LIVENESS_DETECTION)))
        .addMethod(
          getPhotoVerifyMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.bioid.services.Bws.PhotoVerifyRequest,
              com.bioid.services.Bws.PhotoVerifyResponse>(
                service, METHODID_PHOTO_VERIFY)))
        .build();
  }

  private static abstract class BioIDWebServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    BioIDWebServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.bioid.services.Bws.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("BioIDWebService");
    }
  }

  private static final class BioIDWebServiceFileDescriptorSupplier
      extends BioIDWebServiceBaseDescriptorSupplier {
    BioIDWebServiceFileDescriptorSupplier() {}
  }

  private static final class BioIDWebServiceMethodDescriptorSupplier
      extends BioIDWebServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    BioIDWebServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (BioIDWebServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new BioIDWebServiceFileDescriptorSupplier())
              .addMethod(getLivenessDetectionMethod())
              .addMethod(getVideoLivenessDetectionMethod())
              .addMethod(getPhotoVerifyMethod())
              .build();
        }
      }
    }
    return result;
  }
}
