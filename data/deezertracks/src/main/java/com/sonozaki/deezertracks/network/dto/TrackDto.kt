package com.sonozaki.deezertracks.network.dto

import com.google.gson.annotations.SerializedName

data class TrackDto(
    @SerializedName("title")
    val title: String,
    @SerializedName("duration")
    val duration: Long,
    @SerializedName("preview")
    val preview: String,
    @SerializedName("album")
    val album: AlbumDto,
    @SerializedName("artist")
    val artist: ArtistDto
)