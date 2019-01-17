package edu.beuth.movies.models

import javax.persistence.*

@Table(schema = "movies", name = "movies")
class Movie(
        val name: String,
        @Id
        val id: Long
)