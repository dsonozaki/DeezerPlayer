package com.sonozaki.deezertracks.repository

import com.sonozaki.deezertracks.entitities.NetworkTrackStateData
import kotlinx.coroutines.flow.Flow

interface DeezerTracksRepository {
    /**
     * Tracks data
     */
    val tracks: Flow<NetworkTrackStateData>

    /**
     * Search new data with provided query
     */
    suspend fun refreshData(query: String)
}