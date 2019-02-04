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
class TheMovieDbServiceImpl: TheMovieDbService {
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

    override fun generateAllowAccess(token: String): MovieDbAllowAccessDetails {
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

        val responseType = object : ParameterizedTypeReference<FavouriteMoviesResponse>() {}

        val request = RequestEntity.get(
                URI.create(getApiPath("/account/$accountId/favorite/movies") + "&session_id=$sessionId"))
                .accept(MediaType.APPLICATION_JSON).build()

        val response = client.exchange(request, responseType).body ?: return listOf()

        return response.results.map { it.original_title }
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