package com.sonozaki.deezerplayer.mappers.player

import androidx.media3.common.MediaItem
import com.sonozaki.controller.domain.entities.PlayerEvent
import com.sonozaki.data.player.entities.PlayerEventData
import com.sonozaki.data.player.entities.TrackInfo
import com.sonozaki.deezerplayer.mappers.Mapper
import javax.inject.Inject

class PlayerEventDataToEventMapper @Inject constructor(private val trackInfoToMediaItemMapper: TrackInfoToMediaItemMapper) :
    Mapper<PlayerEventData, PlayerEvent> {
    override fun map(t1: PlayerEventData) = when (t1) {
        is PlayerEventData.SelectPlaylistAndTrack -> PlayerEvent.SelectPlaylistAndTrack(
            mapPlayList(
                t1.playList
            ), t1.track
        )
    }

    private fun mapPlayList(t1: List<TrackInfo>): List<MediaItem> = t1.map {
        trackInfoToMediaItemMapper.map(it)
    }
}