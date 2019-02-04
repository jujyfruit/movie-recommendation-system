package edu.beuth.movies.services

import edu.beuth.movies.models.Movie
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface MovieService {
    fun findMovies(searchTerm: String, pageable: Pageable): Page<Movie>
}