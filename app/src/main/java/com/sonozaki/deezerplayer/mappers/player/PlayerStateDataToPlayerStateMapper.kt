package com.sonozaki.deezerplayer.mappers.player

import com.sonozaki.data.player.entities.PlayerStateData
import com.sonozaki.deezerplayer.mappers.Mapper
import com.sonozaki.player.domain.entities.CurrentTrackState
import javax.inject.Inject

class PlayerStateDataToPlayerStateMapper @Inject constructor() :
    Mapper<PlayerStateData, CurrentTrackState> {
    override fun map(t1: PlayerStateData): CurrentTrackState = when (t1) {
        is PlayerStateData.Loading -> CurrentTrackState.Loading
        is PlayerStateData.Data -> CurrentTrackState.Data(
            t1.trackInfo.artist,
            t1.trackInfo.title,
            t1.trackInfo.imageUri
        )
    }
}