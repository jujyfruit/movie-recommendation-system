package edu.beuth.movies.services

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.JsonObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.util.*
import org.json.JSONObject
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.postForObject
import kotlin.collections.HashMap


@Service
class TheMovieDbService {
    @Value("\${themoviedb.apikey}")
    private val apiKey: String? = null

    val client = RestTemplate()

    val apiBaseUrl = "https://api.themoviedb.org/3"
    val websiteBaseUrl = "https://www.themoviedb.org"

    fun createRequestToken(): String {
        val responseType = object : ParameterizedTypeReference<Map<String, String>>() {}

        val request = RequestEntity.get(
                URI.create(getApiPath("/authentication/token/new")))
                .accept(MediaType.APPLICATION_JSON).build()

        val response = client.exchange(request, responseType).body ?: return ""

        return response["request_token"]!!
    }

    fun generateAllowAccess(token: String = createRequestToken()): MovieDbAllowAccessDetails {
        return MovieDbAllowAccessDetails(token, "$websiteBaseUrl/authenticate/$token")
    }

    fun getSessionId(token: String = createRequestToken()): String {
        val obj = JSONObject()
        obj.put("request_token", token)

        val map = HashMap<String, String>()
        map["request_token"] = token
        val result: Map<String, String> =
                client.postForObject(getApiPath("/authentication/session/new"), map, Map::class) ?: return ""

        return result["session_id"]!!
    }

    fun getFavouriteMovies(sessionId: String): List<String> {
        val accountId = getAccountId(sessionId)

        val responseType = object : ParameterizedTypeReference<FavouriteMoviesResponse>() {}

        val request = RequestEntity.get(
                URI.create(getApiPath("/account/$accountId/favorite/movies") + "&session_id=$sessionId"))
                .accept(MediaType.APPLICATION_JSON).build()

        val response = client.exchange(request, responseType).body ?: return listOf()

        return response.results.map { it.original_title }
    }

    private fun getAccountId(sessionId: String): Int? {
        val responseType = object : ParameterizedTypeReference<Account>() {}

        val request = RequestEntity.get(
                URI.create(getApiPath("/account") + "&session_id=$sessionId"))
                .accept(MediaType.APPLICATION_JSON).build()

        val response = client.exchange(request, responseType).body ?: return null

        return response.id
    }

    private fun getApiPath(path: String) = "$apiBaseUrl$path?api_key=$apiKey"
}

class MovieDbAllowAccessDetails(val token: String, val url: String)

class FavouriteMoviesResponse {
    @JsonProperty
    lateinit var results: List<MovieDetails>
}

class MovieDetails {
    @JsonProperty lateinit var original_title: String
}

class Account {
    @JsonProperty var id: Int? = null
}