package com.sonozaki.player.domain.entities

/**
 * Track state
 */
sealed class CurrentTrackState {
    data class Data(val artist: String, val title: String, val imageUri: String) :
        CurrentTrackState()

    data object Loading : CurrentTrackState()
}
