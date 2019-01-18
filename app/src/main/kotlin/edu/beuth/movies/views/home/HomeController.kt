package edu.beuth.movies.views.home

import edu.beuth.movies.models.Movie
import edu.beuth.movies.services.MovieService
import edu.beuth.movies.services.recommender.MovieRecommender
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody


@Controller
class HomeController(private val movieRecommender: MovieRecommender,
                     private val movieService: MovieService) {

    @GetMapping("/home")
    fun greeting(@RequestParam(name = "name", required = false, defaultValue = "World") name: String, model: Model): String {
        model.addAttribute("name", movieRecommender.getMovieRecommendations(listOf()).joinToString())
        return "home"
    }

    @GetMapping("/api/movies")
    @ResponseBody
    fun movies(@RequestParam(name = "searchTerm") searchTerm: String, pageable: Pageable): Page<Movie> {
        return movieService.findMovies(searchTerm, pageable)
    }
}