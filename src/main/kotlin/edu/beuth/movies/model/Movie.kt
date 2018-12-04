package edu.beuth.movies.model

import javax.persistence.*

@Entity
class Movie(
        val name: String,
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long
)