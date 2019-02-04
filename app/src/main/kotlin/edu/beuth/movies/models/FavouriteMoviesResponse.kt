package edu.beuth.movies.models

import com.fasterxml.jackson.annotation.JsonProperty

class FavouriteMoviesResponse {
    @JsonProperty
    lateinit var results: List<MovieDetails>
}
