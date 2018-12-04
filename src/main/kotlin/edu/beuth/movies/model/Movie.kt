package edu.beuth.movies.model

import javax.persistence.*

@Entity(name = "movies")
class Movie(
        val name: String,
        @Id
        val id: Long
)