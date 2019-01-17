package edu.beuth.movies.models

import javax.persistence.*

@Table(schema = "movies", name = "ratings")
class Rating(
        @Id
        val id: Long,
        val rating: Double) {
    @ManyToOne
    lateinit var movie: Movie

    @ManyToOne
    lateinit var user: User

}