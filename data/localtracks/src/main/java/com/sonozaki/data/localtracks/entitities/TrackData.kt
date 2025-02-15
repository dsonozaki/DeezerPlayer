package com.sonozaki.data.localtracks.entitities

data class TrackData(
    val uri: String,
    val duration: Long,
    val coverUri: String,
    val title: String,
    val artist: String
)