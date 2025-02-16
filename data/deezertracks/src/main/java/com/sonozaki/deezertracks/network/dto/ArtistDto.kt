package com.sonozaki.deezertracks.network.dto

import com.google.gson.annotations.SerializedName

data class ArtistDto(
    @SerializedName("name")
    val name: String
)