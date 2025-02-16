package com.sonozaki.deezertracks.network.dto

import com.google.gson.annotations.SerializedName

data class ChartResponseDto(
    @SerializedName("tracks")
    val tracks: TracksDto
)