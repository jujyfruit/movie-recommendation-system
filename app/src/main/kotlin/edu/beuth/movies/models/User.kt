package edu.beuth.movies.models

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(schema = "movies", name = "users")
class User(
        @Id
        val id: Long
)

