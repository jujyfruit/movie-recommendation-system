package edu.beuth.movies

import edu.beuth.movies.models.FavouriteMoviesResponse
import edu.beuth.movies.models.Movie
import edu.beuth.movies.repositories.MovieRepository
import edu.beuth.movies.services.MovieService
import edu.beuth.movies.services.impl.MovieServiceImpl
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class MovieServiceImplTest extends Specification {

    MovieRepository mockMovieRepository
    MovieService service

    def setup() {
        mockMovieRepository = Mock()
        service = new MovieServiceImpl(mockMovieRepository)
    }

    def "should find movies"() {
        given:
        def movies = ["Star Wars", "Star Trek"]
        def searchTerm = "Star"
        def pageRequest = PageRequest.of(1, 10)

        when:
        def result = service.findMovies(searchTerm, pageRequest)

        then:
        1 * mockMovieRepository.findAllByNameContainingIgnoreCase(searchTerm, pageRequest) >>
                new PageImpl<>(movies.collect {it -> new Movie(it, 1)})
        result.numberOfElements == movies.size()
        result.getContent().collect {s -> s.name} == movies
    }
}