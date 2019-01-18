package edu.beuth.movies.services.recommender

import edu.beuth.movies.services.recommender.pb.MovieRecommenderGrpc
import edu.beuth.movies.services.recommender.pb.RecommendMoviesRequest
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class MovieRecommenderGrpcClient : MovieRecommender {
    private val channelBuilder: ManagedChannelBuilder<*>
    private val blockingStub: MovieRecommenderGrpc.MovieRecommenderBlockingStub
    private val channel: ManagedChannel

    constructor(env: Environment) {
        val host = env.getProperty("recommender.host")!!
        val port =  env.getProperty("recommender.port")!!.toInt()

        channelBuilder = ManagedChannelBuilder.forAddress(host, port).usePlaintext()
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

        return blockingStub.recommendMovies(request).recommendedMoviesList
    }
}