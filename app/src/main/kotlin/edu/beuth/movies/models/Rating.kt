package edu.beuth.movies.models

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
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