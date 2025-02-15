package com.sonozaki.deezerplayer.adapters.player

import androidx.media3.common.MediaItem
import com.sonozaki.controller.domain.entities.PlayerError
import com.sonozaki.controller.domain.entities.PlayerEvent
import com.sonozaki.controller.domain.repository.PlayerControllerRepository
import com.sonozaki.data.player.entities.PlayerErrorData
import com.sonozaki.data.player.entities.PlayerEventData
import com.sonozaki.data.player.entities.TrackInfo
import com.sonozaki.data.player.repositories.PlayerEventsRepository
import com.sonozaki.data.player.repositories.PlayerUIRepository
import com.sonozaki.deezerplayer.mappers.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlayerControllerRepositoryAdapter @Inject constructor(
    playerEventsRepository: PlayerEventsRepository,
    private val repository: PlayerUIRepository,
    playerEventMapper: Mapper<PlayerEventData, PlayerEvent>,
    private val playerErrorMapper: Mapper<PlayerError, PlayerErrorData>,
    private val mediaItemMapper: Mapper<MediaItem, TrackInfo>
) : PlayerControllerRepository {
    override val eventFlow: Flow<PlayerEvent> =
        playerEventsRepository.eventFlow.map { playerEventMapper.map(it) }

    override suspend fun setCurrentTrack(track: MediaItem) {
        repository.setCurrentTrack(track.let { mediaItemMapper.map(it) })
    }

    override suspend fun sendException(exception: PlayerError) {
        repository.sendException(playerErrorMapper.map(exception))
    }
}