package com.sonozaki.deezertracks.network.services

import com.sonozaki.deezertracks.network.dto.TracksDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Service for searching tracks with query
 */
interface SearchService {
    @GET(SEARCH_URL)
    suspend fun getSearchResult(
        @Query(
            value = "q",
            encoded = true
        ) query: String
    ): Response<TracksDto>

    companion object {
        private const val SEARCH_URL =
            "/search"
    }
}