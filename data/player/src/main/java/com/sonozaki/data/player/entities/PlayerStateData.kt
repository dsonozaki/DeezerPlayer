package com.sonozaki.data.player.entities

sealed class PlayerStateData {
    data object Loading : PlayerStateData()
    data class Data(val trackInfo: TrackInfo) : PlayerStateData()
}
