package com.sonozaki.data.player.entities

sealed class PlayerEventData {
    data class SelectPlaylistAndTrack(val playList: List<TrackInfo>, val track: Int) :
        PlayerEventData()
}