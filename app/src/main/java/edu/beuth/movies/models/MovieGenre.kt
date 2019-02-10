package edu.beuth.movies.models

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(schema = "movies", name = "movie_genres")
class MovieGenre(
        @Id
        val id: Long) {
    @ManyToOne
    val genre: Genre = Genre("", 0)

    @ManyToOne
    val movie: Movie = Movie("", 0)
}