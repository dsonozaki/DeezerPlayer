package com.sonozaki.data.localtracks.repository

import com.sonozaki.data.localtracks.entitities.TracksStateData
import kotlinx.coroutines.flow.Flow

interface LocalTracksRepository {
    /**
     * Tracks data
     */
    val tracks: Flow<TracksStateData>

    /**
     * Search new data with provided query
     */
    suspend fun refreshData(query: String)
}