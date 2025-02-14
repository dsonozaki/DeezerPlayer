package com.sonozaki.deezerplayer.mappers.player

import com.sonozaki.controller.domain.entities.PlayerError
import com.sonozaki.data.player.entities.PlayerErrorData
import com.sonozaki.deezerplayer.mappers.Mapper
import javax.inject.Inject

class PlayerErrorToDataErrorMapper @Inject constructor() : Mapper<PlayerError, PlayerErrorData> {
    override fun map(t1: PlayerError): PlayerErrorData =
        when (t1) {
            PlayerError.NullTrack -> PlayerErrorData.NullTrack
            PlayerError.PlaybackException -> PlayerErrorData.PlaybackException
        }
}