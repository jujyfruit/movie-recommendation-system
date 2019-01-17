package edu.beuth.movies.services.recommender

import edu.beuth.movies.services.recommender.pb.MovieRecommenderGrpc
import edu.beuth.movies.services.recommender.pb.RecommendMoviesRequest
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class MovieRecommenderGrpcClient : MovieRecommender {
    private val channelBuilder: ManagedChannelBuilder<*>
    private val blockingStub: MovieRecommenderGrpc.MovieRecommenderBlockingStub
    private val channel: ManagedChannel

    constructor() {
//        @Value("\${recommender.host}") host
//    }: String? = null,
//                @Value("\${recommender.port}") port: Int? = null) {
        // TODO fix this
        print("vals:")
        val host = "localhost"
        val port = 50051
        print(host)
        print(port)
        channelBuilder = ManagedChannelBuilder.forAddress(host!!, port!!).usePlaintext()
        channel = channelBuilder.build()
        blockingStub = MovieRecommenderGrpc.newBlockingStub(channel)
    }

    @Throws(InterruptedException::class)
    fun shutdown() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }

    override fun getMovieRecommendations(referenceMovies: List<String>): List<String> {
        val request = RecommendMoviesRequest.newBuilder()
                .addReferenceMovies("test")
                .build()
        print("req" + request)

        return blockingStub.recommendMovies(request).recommendedMoviesList
    }
}