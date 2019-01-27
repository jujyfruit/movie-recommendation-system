package edu.beuth.movies.services

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.web.client.RestTemplate
import java.net.URI


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

    fun generateAllowAccessUrl(token: String = createRequestToken()): String {
        val responseType = object : ParameterizedTypeReference<Map<String, String>>() {}

        val request = RequestEntity.get(
                URI.create("$websiteBaseUrl/authenticate/$token"))
                .accept(MediaType.APPLICATION_JSON).build()

        val response = client.exchange(request, responseType).body ?: return ""

        return response["request_token"]!!
    }

    private fun getApiPath(path: String) = "$apiBaseUrl$path?api_key=$apiKey"
}