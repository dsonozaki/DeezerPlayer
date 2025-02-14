package com.sonozaki.data.player.repositories

import com.sonozaki.data.player.entities.PlayerErrorData
import com.sonozaki.data.player.entities.PlayerStateData
import com.sonozaki.data.player.entities.TrackInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * Repository representing player state.
 */
interface PlayerUIRepository {
    /**
     * Data about current track
     */
    val playerState: StateFlow<PlayerStateData>

    /**
     * Player errors
     */
    val errorEvents: Flow<PlayerErrorData>

    /**
     * Send exception data
     */
    suspend fun sendException(exception: PlayerErrorData)

    /**
     * Send current playing track data
     */
    suspend fun setCurrentTrack(trackInfo: TrackInfo)
}