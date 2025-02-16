package com.sonozaki.deezertracks.network.dto

import com.google.gson.annotations.SerializedName

data class TracksDto(
    @SerializedName("data")
    val data: List<TrackDto>
)