package edu.beuth.movies.repositories

import edu.beuth.movies.models.Movie
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface MovieRepository : JpaRepository<Movie, Long> {
    fun findAllByNameContainingIgnoreCase(searchTerm: String, pageable: Pageable): Page<Movie>
}