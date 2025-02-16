package com.sonozaki.controller.domain.entities

import androidx.media3.common.MediaItem

/**
 * Commands to player
 */
sealed class PlayerEvent {
    /**
     * Select playlist and start playing a track with provided id
     */
    data class SelectPlaylistAndTrack(val playList: List<MediaItem>, val track: Int) : PlayerEvent()
}