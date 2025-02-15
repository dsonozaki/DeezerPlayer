package com.sonozaki.data.localtracks.entitities

/**
 * Possible errors when loading tracks
 */
sealed class TrackError {
    data object DataNotRetrieved : TrackError()
    data class CustomException(val exception: String) : TrackError()
}