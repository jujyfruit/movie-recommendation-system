package edu.beuth.movies.models

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(schema = "movies", name = "genres")
class Genre(
        val name: String,
        @Id
        val id: Long
)