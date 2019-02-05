package edu.beuth.movies.services.impl

import java.net.URI
import java.util

import edu.beuth.movies.models.{FavouriteMoviesResponse, MovieDbAccount, MovieDbAllowAccessDetails}
import edu.beuth.movies.services.TheMovieDbService
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.{MediaType, RequestEntity}
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class TheMovieDbServiceImpl extends TheMovieDbService {
  @Value("${themoviedb.apikey}")
  private val apiKey: String = null

  val client = new RestTemplate()

  val apiBaseUrl = "https://api.themoviedb.org/3"
  val websiteBaseUrl = "https://www.themoviedb.org"

  override def createRequestToken(): String = {
    val responseType = new ParameterizedTypeReference[java.util.HashMap[String, String]]() {}

    val str = getApiPath("/authentication/token/new")
    val request = RequestEntity.get(
      URI.create(str))
      .accept(MediaType.APPLICATION_JSON).build()

    val response = client.exchange(request, responseType)
    if (response == null) {
      return ""
    }

    response.getBody.getOrDefault("request_token", "")
  }

  override def generateAllowAccess(token: String): MovieDbAllowAccessDetails =
    new MovieDbAllowAccessDetails(token, s"$websiteBaseUrl/authenticate/$token")

  override def getSessionId(token: String): String = {
    val obj = new JSONObject()
    obj.put("request_token", token)

    val map = new util.HashMap[String, String]() {
      {
        put("request_token", token)
      }
    }

    val result: util.Map[String, String] =
      client.postForObject(getApiPath("/authentication/session/new"), map, classOf[util.HashMap[String, String]])
    if (result == null) {
      return ""
    }

    result.getOrDefault("session_id", "")
  }

  override def getFavouriteMovies(sessionId: String): util.List[String] = {
    val accountId = getAccountId(sessionId)

    var page = 1
    var favouriteMoviesPaged: FavouriteMoviesResponse = getFavouriteMoviesPaged(sessionId, accountId, page)
    if (favouriteMoviesPaged == null) {
      return util.Collections.emptyList()
    }

    val favouriteMovies: util.List[String] = new util.ArrayList()
    favouriteMovies.addAll(favouriteMoviesPaged.getMovieTitles)

    while (page < favouriteMoviesPaged.getTotal_pages) {
      page = page + 1
      favouriteMoviesPaged = getFavouriteMoviesPaged(sessionId, accountId, page)
      if (favouriteMoviesPaged == null) {
        return favouriteMovies
      }

      favouriteMovies.addAll(favouriteMoviesPaged.getMovieTitles)
    }

    favouriteMovies

  }

  private def getFavouriteMoviesPaged(sessionId: String, accountId: Int, page: Int): FavouriteMoviesResponse = {
    val responseType = new ParameterizedTypeReference[FavouriteMoviesResponse]() {}

    val request = RequestEntity.get(
      URI.create(getApiPath(s"/account/$accountId/favorite/movies") + s"&session_id=$sessionId&page=$page"))
      .accept(MediaType.APPLICATION_JSON).build()

    client.exchange(request, responseType).getBody
  }


  private def getAccountId(sessionId: String): Int = {
    val responseType = new ParameterizedTypeReference[MovieDbAccount]() {}

    val request = RequestEntity.get(
      URI.create(getApiPath("/account") + s"&session_id=$sessionId"))
      .accept(MediaType.APPLICATION_JSON).build()

    val response = client.exchange(request, responseType).getBody
    if (response == null) {
      return -1
    }

    response.getId
  }

  private def getApiPath(path: String): String = s"$apiBaseUrl$path?api_key=$apiKey"
}