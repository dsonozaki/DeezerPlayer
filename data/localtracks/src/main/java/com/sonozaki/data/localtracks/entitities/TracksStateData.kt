package com.sonozaki.data.localtracks.entitities

/**
 * Tracks data.
 */
sealed class TracksStateData {
    data object Loading : TracksStateData()
    data class Data(val tracks: List<TrackData>) : TracksStateData()
    data class Error(val error: TrackError) : TracksStateData()
}