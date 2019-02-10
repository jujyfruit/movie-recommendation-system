package edu.beuth.movies.services.impl

import edu.beuth.movies.models.FavouriteMoviesResponse
import edu.beuth.movies.models.MovieDbAccount
import edu.beuth.movies.models.MovieDbAllowAccessDetails
import edu.beuth.movies.services.TheMovieDbService
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForObject
import java.net.URI


@Service
class TheMovieDbServiceImpl : TheMovieDbService {
    @Value("\${themoviedb.apikey}")
    private val apiKey: String? = null

    val client = RestTemplate()

    val apiBaseUrl = "https://api.themoviedb.org/3"
    val websiteBaseUrl = "https://www.themoviedb.org"

    override fun createRequestToken(): String {
        val responseType = object : ParameterizedTypeReference<Map<String, String>>() {}

        val request = RequestEntity.get(
                URI.create(getApiPath("/authentication/token/new")))
                .accept(MediaType.APPLICATION_JSON).build()

        val response = client.exchange(request, responseType).body ?: return ""

        return response["request_token"]!!
    }

    override fun generateAllowAccessDetails(tokenProvider: () -> String): MovieDbAllowAccessDetails {
        val token = tokenProvider()
        return MovieDbAllowAccessDetails(token, "$websiteBaseUrl/authenticate/$token")
    }

    override fun getSessionId(token: String): String {
        val obj = JSONObject()
        obj.put("request_token", token)

        val map = HashMap<String, String>()
        map["request_token"] = token
        val result: Map<String, String> =
                client.postForObject(getApiPath("/authentication/session/new"), map, Map::class) ?: return ""

        return result["session_id"]!!
    }

    override fun getFavouriteMovies(sessionId: String): List<String> {
        val accountId = getAccountId(sessionId)

        val favouriteMoviesPaged: FavouriteMoviesResponse? = getFavouriteMoviesPaged(sessionId, accountId, 1)
                ?: return emptyList()

        val favouriteMovies = arrayListOf<String>()
        favouriteMovies.addAll(favouriteMoviesPaged!!.getMovieTitles())

        return collectAllFavouriteMovies(sessionId, accountId, favouriteMovies, favouriteMoviesPaged.total_pages, 2)
    }

    private fun collectAllFavouriteMovies(sessionId: String, accountId: Int?, movies: List<String>,
                                          totalPages: Int, page: Int): List<String> {
        if (page >= totalPages) {
            return listOf()
        }

        val favouriteMoviesPaged = getFavouriteMoviesPaged(sessionId, accountId, page) ?: return movies
        val favouriteMovies = favouriteMoviesPaged.getMovieTitles()
        return movies + favouriteMovies + collectAllFavouriteMovies(sessionId, accountId, movies, totalPages, page + 1)
    }

    private fun getFavouriteMoviesPaged(sessionId: String, accountId: Int?, page: Int): FavouriteMoviesResponse? {

        val responseType = object : ParameterizedTypeReference<FavouriteMoviesResponse>() {}

        val request = RequestEntity.get(
                URI.create(getApiPath("/account/$accountId/favorite/movies") + "&session_id=$sessionId&page=$page"))
                .accept(MediaType.APPLICATION_JSON).build()

        return client.exchange(request, responseType).body

    }

    private fun getAccountId(sessionId: String): Int? {
        val responseType = object : ParameterizedTypeReference<MovieDbAccount>() {}

        val request = RequestEntity.get(
                URI.create(getApiPath("/account") + "&session_id=$sessionId"))
                .accept(MediaType.APPLICATION_JSON).build()

        val response = client.exchange(request, responseType).body ?: return null

        return response.id
    }

    private fun getApiPath(path: String) = "$apiBaseUrl$path?api_key=$apiKey"
}