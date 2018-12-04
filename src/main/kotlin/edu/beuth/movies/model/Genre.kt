package edu.beuth.movies.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Genre(
        val name: String,
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long
)