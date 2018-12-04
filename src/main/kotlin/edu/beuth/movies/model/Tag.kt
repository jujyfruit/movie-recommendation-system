package edu.beuth.movies.model

import javax.persistence.*

@Entity(name="tags")
class Tag(
        @Id
        val id: Long,
        val name: String) {

    @ManyToOne
    lateinit var movie: Movie
}