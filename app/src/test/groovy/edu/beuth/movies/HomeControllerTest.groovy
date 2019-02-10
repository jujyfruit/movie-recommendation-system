package edu.beuth.movies

import edu.beuth.movies.models.Movie
import edu.beuth.movies.models.MovieDbAllowAccessDetails
import edu.beuth.movies.services.MovieService
import edu.beuth.movies.services.TheMovieDbService
import edu.beuth.movies.services.recommender.MovieRecommender
import edu.beuth.movies.views.home.HomeController
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class HomeControllerTest extends Specification {

    HomeController controller

    MovieRecommender mockMovieRecommender
    MovieService mockMovieService
    TheMovieDbService mockTheMovieDbService

    def setup() {
        mockMovieRecommender = Mock()
        mockMovieService = Mock()
        mockTheMovieDbService = Mock()

        controller = new HomeController(mockMovieRecommender, mockMovieService, mockTheMovieDbService)
    }

    def "should return home template name"() {
        when:
        def result = controller.home()

        then:
        result == "home"
    }

    def "should find movies"() {
        given:
        def movies = ["Star Wars", "Star Trek"]
        def searchTerm = "Star"
        def pageRequest = PageRequest.of(1, 10)

        when:
        def result = controller.findMovies(searchTerm, pageRequest)

        then:
        1 * mockMovieService.findMovies(searchTerm, pageRequest) >>
                new PageImpl<>(movies.collect {it -> new Movie(it, 1)})
        result.numberOfElements == movies.size()
        result.getContent().collect {s -> s.name} == movies
    }

    def "should get movie recommendations"() {
        given:
        def referenceMovies = ["Star Wars: Episode I", "Star Wars: Episode II"]
        def recommendedMovies = ["Star Wars: Episode II", "Star Wars: Episode IV"]

        when:
        def result = controller.getRecommendations(referenceMovies)

        then:
        1 * mockMovieRecommender.getMovieRecommendations(referenceMovies) >> recommendedMovies
        result == recommendedMovies
    }

    def "should generate themoviedb allow access details"() {
        given:
        def token = "sample-token"
        def url = "sample-url"

        when:
        def result = controller.getMovieDbAllowAccess()

        then:
        1 * mockTheMovieDbService.generateAllowAccessDetails(_) >> new MovieDbAllowAccessDetails(token, url)
        result != null
        result.token == token
        result.url == url
    }

    def "should generate themoviedb allow access url"() {
        given:
        def token = "sample-token"
        def sessionId = "session-123"

        when:
        def result = controller.getMovieDbAllowAccessUrl(token)

        then:
        1 * mockTheMovieDbService.getSessionId(token) >> sessionId
        result == sessionId
    }

    def "should return themoviedb favourite movies"() {
        given:
        def sessionId = "session-123"
        def favouriteMovies = ["Star Wars: Episode I", "Star Wars: Episode II"]

        when:
        def result = controller.getMovieDbFavouriteMovies(sessionId)

        then:
        1 * mockTheMovieDbService.getFavouriteMovies(sessionId) >> favouriteMovies
        result == favouriteMovies
    }
}