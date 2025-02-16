package com.sonozaki.tracklist.domain.entities

import com.sonozaki.core.presentation.UIError

/**
 * Tracks data.
 */
sealed class TrackState {
    data object Loading : TrackState()
    data class Data(val tracks: List<Track>) : TrackState()
    data class Error(val error: UIError) : TrackState()
}