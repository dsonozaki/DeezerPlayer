package com.sonozaki.deezertracks.network.services

import com.sonozaki.deezertracks.network.dto.ChartResponseDto
import retrofit2.Response
import retrofit2.http.GET

/**
 * Service for loading tracks from chart
 */
interface ChartService {
    @GET(HOTEL_DETAILS_URL)
    suspend fun getCharts(): Response<ChartResponseDto>

    companion object {
        private const val HOTEL_DETAILS_URL =
            "/chart"
    }
}