package edu.beuth.movies.model

import javax.persistence.*

@Entity
class Rating(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long,
        val rating: Double) {
    @ManyToOne
    lateinit var movie: Movie

    @ManyToOne
    lateinit var user: User

}