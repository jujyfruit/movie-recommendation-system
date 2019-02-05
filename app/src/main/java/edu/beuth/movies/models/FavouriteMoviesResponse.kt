package edu.beuth.movies.models

import com.fasterxml.jackson.annotation.JsonProperty

class FavouriteMoviesResponse {
    @JsonProperty
    val results: List<MovieDetails> = listOf()

    @JsonProperty
    val total_pages: Int = 1

    fun getMovieTitles(): List<String> = results.map { it.original_title }
}
