package com.sonozaki.tracklist.domain.entities

data class Track(
    val uri: String,
    val duration: Long,
    val coverUri: String,
    val title: String,
    val artist: String
)