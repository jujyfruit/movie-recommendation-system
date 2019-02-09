package edu.beuth.movies.services

import edu.beuth.movies.models.MovieDbAllowAccessDetails

interface TheMovieDbService {
    fun createRequestToken(): String

    fun generateAllowAccessDetails(token: String = createRequestToken()): MovieDbAllowAccessDetails

    fun getSessionId(token: String): String

    fun getFavouriteMovies(sessionId: String): List<String>
}