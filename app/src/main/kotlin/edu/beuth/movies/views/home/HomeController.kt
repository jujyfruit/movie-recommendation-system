package edu.beuth.movies.views.home

import edu.beuth.movies.services.recommender.MovieRecommender
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam


@Controller
class HomeController(private val movieRecommender: MovieRecommender) {

    @GetMapping("/home")
    fun greeting(@RequestParam(name = "name", required = false, defaultValue = "World") name: String, model: Model): String {
        model.addAttribute("name", movieRecommender.getMovieRecommendations(listOf()).joinToString())
        return "home"
    }
}