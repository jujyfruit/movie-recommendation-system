package edu.beuth.movies.model

import javax.persistence.*

@Entity
class Tag(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long,
        val name: String) {

    @ManyToOne
    lateinit var movie: Movie
}