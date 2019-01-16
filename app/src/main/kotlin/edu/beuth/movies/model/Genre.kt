package edu.beuth.movies.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "genres")
class Genre(
        val name: String,
        @Id
        val id: Long
)