package edu.beuth.movies.services.recommender.pb;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.9.1)",
    comments = "Source: recommender.proto")
public final class MovieRecommenderGrpc {

  private MovieRecommenderGrpc() {}

  public static final String SERVICE_NAME = "pb.MovieRecommender";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  @java.lang.Deprecated // Use {@link #getRecommendMoviesMethod()} instead. 
  public static final io.grpc.MethodDescriptor<edu.beuth.movies.services.recommender.pb.RecommendMoviesRequest,
      edu.beuth.movies.services.recommender.pb.RecommendMoviesResponse> METHOD_RECOMMEND_MOVIES = getRecommendMoviesMethod();

  private static volatile io.grpc.MethodDescriptor<edu.beuth.movies.services.recommender.pb.RecommendMoviesRequest,
      edu.beuth.movies.services.recommender.pb.RecommendMoviesResponse> getRecommendMoviesMethod;

  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static io.grpc.MethodDescriptor<edu.beuth.movies.services.recommender.pb.RecommendMoviesRequest,
      edu.beuth.movies.services.recommender.pb.RecommendMoviesResponse> getRecommendMoviesMethod() {
    io.grpc.MethodDescriptor<edu.beuth.movies.services.recommender.pb.RecommendMoviesRequest, edu.beuth.movies.services.recommender.pb.RecommendMoviesResponse> getRecommendMoviesMethod;
    if ((getRecommendMoviesMethod = MovieRecommenderGrpc.getRecommendMoviesMethod) == null) {
      synchronized (MovieRecommenderGrpc.class) {
        if ((getRecommendMoviesMethod = MovieRecommenderGrpc.getRecommendMoviesMethod) == null) {
          MovieRecommenderGrpc.getRecommendMoviesMethod = getRecommendMoviesMethod = 
              io.grpc.MethodDescriptor.<edu.beuth.movies.services.recommender.pb.RecommendMoviesRequest, edu.beuth.movies.services.recommender.pb.RecommendMoviesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "pb.MovieRecommender", "recommend_movies"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  edu.beuth.movies.services.recommender.pb.RecommendMoviesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  edu.beuth.movies.services.recommender.pb.RecommendMoviesResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new MovieRecommenderMethodDescriptorSupplier("recommend_movies"))
                  .build();
          }
        }
     }
     return getRecommendMoviesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static MovieRecommenderStub newStub(io.grpc.Channel channel) {
    return new MovieRecommenderStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static MovieRecommenderBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new MovieRecommenderBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static MovieRecommenderFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new MovieRecommenderFutureStub(channel);
  }

  /**
   */
  public static abstract class MovieRecommenderImplBase implements io.grpc.BindableService {

    /**
     */
    public void recommendMovies(edu.beuth.movies.services.recommender.pb.RecommendMoviesRequest request,
        io.grpc.stub.StreamObserver<edu.beuth.movies.services.recommender.pb.RecommendMoviesResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRecommendMoviesMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRecommendMoviesMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                edu.beuth.movies.services.recommender.pb.RecommendMoviesRequest,
                edu.beuth.movies.services.recommender.pb.RecommendMoviesResponse>(
                  this, METHODID_RECOMMEND_MOVIES)))
          .build();
    }
  }

  /**
   */
  public static final class MovieRecommenderStub extends io.grpc.stub.AbstractStub<MovieRecommenderStub> {
    private MovieRecommenderStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MovieRecommenderStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MovieRecommenderStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MovieRecommenderStub(channel, callOptions);
    }

    /**
     */
    public void recommendMovies(edu.beuth.movies.services.recommender.pb.RecommendMoviesRequest request,
        io.grpc.stub.StreamObserver<edu.beuth.movies.services.recommender.pb.RecommendMoviesResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRecommendMoviesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class MovieRecommenderBlockingStub extends io.grpc.stub.AbstractStub<MovieRecommenderBlockingStub> {
    private MovieRecommenderBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MovieRecommenderBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MovieRecommenderBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MovieRecommenderBlockingStub(channel, callOptions);
    }

    /**
     */
    public edu.beuth.movies.services.recommender.pb.RecommendMoviesResponse recommendMovies(edu.beuth.movies.services.recommender.pb.RecommendMoviesRequest request) {
      return blockingUnaryCall(
          getChannel(), getRecommendMoviesMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class MovieRecommenderFutureStub extends io.grpc.stub.AbstractStub<MovieRecommenderFutureStub> {
    private MovieRecommenderFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private MovieRecommenderFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected MovieRecommenderFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new MovieRecommenderFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<edu.beuth.movies.services.recommender.pb.RecommendMoviesResponse> recommendMovies(
        edu.beuth.movies.services.recommender.pb.RecommendMoviesRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRecommendMoviesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_RECOMMEND_MOVIES = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final MovieRecommenderImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(MovieRecommenderImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_RECOMMEND_MOVIES:
          serviceImpl.recommendMovies((edu.beuth.movies.services.recommender.pb.RecommendMoviesRequest) request,
              (io.grpc.stub.StreamObserver<edu.beuth.movies.services.recommender.pb.RecommendMoviesResponse>) responseObserver);
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

  private static abstract class MovieRecommenderBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    MovieRecommenderBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return edu.beuth.movies.services.recommender.pb.Recommender.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("MovieRecommender");
    }
  }

  private static final class MovieRecommenderFileDescriptorSupplier
      extends MovieRecommenderBaseDescriptorSupplier {
    MovieRecommenderFileDescriptorSupplier() {}
  }

  private static final class MovieRecommenderMethodDescriptorSupplier
      extends MovieRecommenderBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    MovieRecommenderMethodDescriptorSupplier(String methodName) {
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
      synchronized (MovieRecommenderGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new MovieRecommenderFileDescriptorSupplier())
              .addMethod(getRecommendMoviesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
