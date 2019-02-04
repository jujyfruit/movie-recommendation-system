package edu.beuth.movies.models

import com.fasterxml.jackson.annotation.JsonProperty

class MovieDetails {
    @JsonProperty
    lateinit var original_title: String
}