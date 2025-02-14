package com.sonozaki.deezerplayer.mappers.player

import com.sonozaki.core.presentation.UIError
import com.sonozaki.data.player.entities.PlayerErrorData
import com.sonozaki.deezerplayer.mappers.Mapper
import javax.inject.Inject

class PlayerDataToUIErrorMapper @Inject constructor() : Mapper<PlayerErrorData, UIError> {
    override fun map(t1: PlayerErrorData): UIError =
        when (t1) {
            PlayerErrorData.NullTrack -> UIError(com.sonozaki.player.R.string.null_track_exception)
            PlayerErrorData.PlaybackException -> UIError(com.sonozaki.player.R.string.playback_exception)
        }
}