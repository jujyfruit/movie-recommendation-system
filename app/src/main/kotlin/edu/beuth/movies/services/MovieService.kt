package edu.beuth.movies.services

import edu.beuth.movies.models.Movie
import edu.beuth.movies.repositories.MovieRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class MovieService(private val repository: MovieRepository) {

    fun findMovies(searchTerm: String, pageable: Pageable): Page<Movie> {
        return repository.findAllByNameContainingIgnoreCase(searchTerm, pageable)
    }
}