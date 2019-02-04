package edu.beuth.movies.services.recommender

interface MovieRecommender {
    fun getMovieRecommendations(referenceMovies: List<String>): List<String>
}