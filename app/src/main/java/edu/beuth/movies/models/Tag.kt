package edu.beuth.movies.models

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(schema = "movies", name = "tags")
class Tag(
        @Id
        val id: Long,
        val name: String)