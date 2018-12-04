package edu.beuth.movies.model

import javax.persistence.*

@Entity(name="ratings")
class Rating(
        @Id
        val id: Long,
        val rating: Double) {
    @ManyToOne
    lateinit var movie: Movie

    @ManyToOne
    lateinit var user: User

}