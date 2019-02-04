package edu.beuth.movies.models

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(schema = "movies", name = "movies")
class Movie(
        val name: String,
        @Id
        val id: Long
)