package edu.beuth.movies.model

import javax.persistence.*

@Entity(name="movies_tags")
class MovieTag(
        @Id
        val id: Long) {
    @ManyToOne
    lateinit var tag: Tag

    @ManyToOne
    lateinit var movie: Movie

    @ManyToOne
    lateinit var user: User

}