package edu.beuth.movies.views.home

import edu.beuth.movies.models.Movie
import edu.beuth.movies.models.MovieDbAllowAccessDetails
import edu.beuth.movies.services.MovieService
import edu.beuth.movies.services.impl.TheMovieDbServiceImpl
import edu.beuth.movies.services.recommender.MovieRecommender
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody


@Controller
class HomeController(private val movieRecommender: MovieRecommender,
                     private val movieService: MovieService,
                     private val theMovieDbService: TheMovieDbServiceImpl) {

    @GetMapping("/")
    fun home(): String {
        return "home"
    }

    @GetMapping("/api/movies")
    @ResponseBody
    fun movies(@RequestParam(name = "searchTerm") searchTerm: String, pageable: Pageable): Page<Movie> {
        return movieService.findMovies(searchTerm, pageable)
    }

    @GetMapping("/api/getRecommendations")
    @ResponseBody
    fun getRecommendations(@RequestParam(name = "referenceMovies[]") referenceMovies: List<String>): List<String> {
        return movieRecommender.getMovieRecommendations(referenceMovies)
    }

    @GetMapping("/api/getMovieDbAllowAccess")
    @ResponseBody
    fun getMovieDbAllowAccess(): MovieDbAllowAccessDetails {
        return theMovieDbService.generateAllowAccess()
    }

    @GetMapping("/api/getMovieDbSessionId")
    @ResponseBody
    fun getMovieDbAllowAccessUrl(@RequestParam token: String): String {
        return theMovieDbService.getSessionId(token)
    }

    @GetMapping("/api/getMovieDbFavouriteMovies")
    @ResponseBody
    fun getMovieDbFavouriteMovies(@RequestParam sessionId: String): List<String> {
        return theMovieDbService.getFavouriteMovies(sessionId)
    }
}