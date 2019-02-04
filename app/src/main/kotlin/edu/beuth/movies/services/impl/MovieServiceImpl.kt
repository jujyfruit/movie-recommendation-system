package edu.beuth.movies.services.impl

import edu.beuth.movies.models.Movie
import edu.beuth.movies.repositories.MovieRepository
import edu.beuth.movies.services.MovieService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class MovieServiceImpl(private val repository: MovieRepository): MovieService {

    override fun findMovies(searchTerm: String, pageable: Pageable): Page<Movie> {
        return repository.findAllByNameContainingIgnoreCase(searchTerm, pageable)
    }
}