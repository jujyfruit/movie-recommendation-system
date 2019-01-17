package edu.beuth.movies.services.recommender

import edu.beuth.movies.services.recommender.pb.MovieRecommenderGrpc
import edu.beuth.movies.services.recommender.pb.Recommender
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import java.util.concurrent.TimeUnit

class MovieRecommenderGrpcClient(channelBuilder: ManagedChannelBuilder<*>) : MovieRecommender {
    private val channel: ManagedChannel = channelBuilder.build()
    private val blockingStub: MovieRecommenderGrpc.MovieRecommenderBlockingStub

    constructor(host: String, port: Int) : this(ManagedChannelBuilder.forAddress(host, port).usePlaintext())

    init {
        blockingStub = MovieRecommenderGrpc.newBlockingStub(channel)
    }

    @Throws(InterruptedException::class)
    fun shutdown() {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }

    override fun getMovieRecommendations(referenceMovies: List<String>): List<String> {
        val request = Recommender.RecommendMoviesRequest.newBuilder()
                .build()

        return blockingStub.recommendMovies(request).recommendedMoviesList
    }
}