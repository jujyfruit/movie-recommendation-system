package edu.beuth.movies.models

import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Table(schema = "movies", name = "movie_genres")
class MovieGenre(
        @Id
        val id: Long) {
    @ManyToOne
    lateinit var genre: Genre

    @ManyToOne
    lateinit var movie: Movie

}