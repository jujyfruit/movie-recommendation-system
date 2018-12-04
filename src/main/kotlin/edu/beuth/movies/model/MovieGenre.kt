package edu.beuth.movies.model

import javax.persistence.*

@Entity
class MovieGenre(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long) {
    @ManyToOne
    lateinit var genre: Genre

    @ManyToMany
    lateinit var movie: Movie

}