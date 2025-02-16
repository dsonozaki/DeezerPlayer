package com.sonozaki.controller.domain.repository

import androidx.media3.common.MediaItem
import com.sonozaki.controller.domain.entities.PlayerError
import com.sonozaki.controller.domain.entities.PlayerEvent
import kotlinx.coroutines.flow.Flow

interface PlayerControllerRepository {
    /**
     * Get flow of player
     */
    val eventFlow: Flow<PlayerEvent>

    /**
     * Emit current track state
     */
    suspend fun setCurrentTrack(track: MediaItem)

    /**
     * Emit exception
     */
    suspend fun sendException(exception: PlayerError)
}