package com.sonozaki.tracklist.domain.repository

import com.sonozaki.tracklist.domain.entities.Track
import com.sonozaki.tracklist.domain.entities.TrackState
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    /**
     * Get tracks data
     */
    val tracksState: Flow<TrackState>
    /**
     * Load new tracks
     */
    suspend fun refresh(searchQuery: String)
    /**
     * Send track list and track id to player
     */
    suspend fun setTrackListAndTrack(trackList: List<Track>, id: Int)
}