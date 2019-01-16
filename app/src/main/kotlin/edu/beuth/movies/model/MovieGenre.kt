package edu.beuth.movies.model

import javax.persistence.*

@Entity(name="movie_genres")
class MovieGenre(
        @Id
        val id: Long) {
    @ManyToOne
    lateinit var genre: Genre

    @ManyToOne
    lateinit var movie: Movie

}