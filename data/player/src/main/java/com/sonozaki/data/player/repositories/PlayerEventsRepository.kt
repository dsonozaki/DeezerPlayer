package com.sonozaki.data.player.repositories

import com.sonozaki.data.player.entities.PlayerEventData
import com.sonozaki.data.player.entities.TrackInfo
import kotlinx.coroutines.flow.Flow

/**
 * Repository for player controlling
 */
interface PlayerEventsRepository {
    /**
     * Flow of commands to player
     */
    val eventFlow: Flow<PlayerEventData>

    /**
     * Send command to select playlist and play track with provided id
     */
    suspend fun selectPlaylistAndTrack(playList: List<TrackInfo>, track: Int)
}