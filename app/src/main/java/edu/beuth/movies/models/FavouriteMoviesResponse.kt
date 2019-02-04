package edu.beuth.movies.models

import com.fasterxml.jackson.annotation.JsonProperty

class FavouriteMoviesResponse {
    @JsonProperty
    lateinit var results: List<MovieDetails>

    @JsonProperty
    val total_pages: Int = 1

    fun getMovieTitles(): List<String> {
        return results.map { it.original_title }
    }
}
