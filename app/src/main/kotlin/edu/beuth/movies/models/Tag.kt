package edu.beuth.movies.models

import javax.persistence.Id
import javax.persistence.Table

@Table(schema = "movies", name = "tags")
class Tag(
        @Id
        val id: Long,
        val name: String)