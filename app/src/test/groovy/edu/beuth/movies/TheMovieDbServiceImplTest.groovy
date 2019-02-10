package edu.beuth.movies

import edu.beuth.movies.models.FavouriteMoviesResponse
import edu.beuth.movies.models.MovieDbAccount
import edu.beuth.movies.models.MovieDetails
import edu.beuth.movies.services.impl.TheMovieDbServiceImpl
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class TheMovieDbServiceImplTest extends Specification {

    TheMovieDbServiceImpl service
    RestTemplate mockClient

    def setup() {
        mockClient = Mock()
        service = new TheMovieDbServiceImpl(mockClient)
    }

    def "should create request token"() {
        given:
        def token = "sample-token"
        def apiResponse = new HashMap()
        apiResponse.put("request_token", token)

        when:
        def result = service.createRequestToken()

        then:
        1 * mockClient.exchange(_, _) >> new ResponseEntity<Map<String, String>>(apiResponse, HttpStatus.OK)
        result.invoke() == token
    }

    def "should generate allow access details"() {
        given:
        def token = "sample-token"

        when:
        def result = service.generateAllowAccessDetails({ it -> token })

        then:
        result.token == token
        result.url.endsWith(token)
    }

    def "should get themoviedb session id"() {
        given:
        def token = "sample-token"
        def sessionId = "session-123"
        def map = new HashMap()
        map.put("session_id", sessionId)

        when:
        def result = service.getSessionId(token)

        then:
        1 * mockClient.postForObject(_, _, _, _) >> map
        result == sessionId
    }


    def "should get favourite movies"() {
        given:
        def sessionId = "session-123"
        def movies = ["Star Wars", "Star Trek"]
        def response = new FavouriteMoviesResponse(movies.collect { new MovieDetails(it) }, 2)
        def favouriteMoviesResponseType = new ParameterizedTypeReference<FavouriteMoviesResponse>(){}

        def movieDbAccountResponse = new MovieDbAccount()
        def movieDbAccountResponseType = new ParameterizedTypeReference<MovieDbAccount>(){}

        when:
        def result = service.getFavouriteMovies(sessionId)

        then:
        1 * mockClient.exchange(_, favouriteMoviesResponseType) >> new ResponseEntity<>(response, HttpStatus.OK)
        1 * mockClient.exchange(_, movieDbAccountResponseType) >> new ResponseEntity<>(movieDbAccountResponse, HttpStatus.OK)

        result == movies
    }
}