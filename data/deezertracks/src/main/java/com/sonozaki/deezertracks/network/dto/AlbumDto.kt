package com.sonozaki.deezertracks.network.dto

import com.google.gson.annotations.SerializedName

data class AlbumDto(
    @SerializedName("cover_medium")
    val pictureMedium: String
)