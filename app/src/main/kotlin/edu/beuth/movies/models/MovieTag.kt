package edu.beuth.movies.models

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(schema = "movies", name = "movies_tags")
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