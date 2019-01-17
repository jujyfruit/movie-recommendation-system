package edu.beuth.movies.models

import javax.persistence.Id
import javax.persistence.Table

@Table(schema = "movies", name = "users")
class User(
        @Id
        val id: Long
)

