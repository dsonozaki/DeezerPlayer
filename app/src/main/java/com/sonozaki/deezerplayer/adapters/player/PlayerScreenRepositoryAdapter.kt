package com.sonozaki.deezerplayer.adapters.player

import com.sonozaki.core.presentation.UIError
import com.sonozaki.data.player.entities.PlayerErrorData
import com.sonozaki.data.player.entities.PlayerStateData
import com.sonozaki.data.player.repositories.PlayerUIRepository
import com.sonozaki.deezerplayer.mappers.Mapper
import com.sonozaki.player.domain.entities.CurrentTrackState
import com.sonozaki.player.domain.repositories.PlayerScreenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlayerScreenRepositoryAdapter @Inject constructor(
    uiRepository: PlayerUIRepository,
    private val playerStateMapper: Mapper<PlayerStateData, CurrentTrackState>,
    private val playerDataToUIErrorMapper: Mapper<PlayerErrorData, UIError>
) : PlayerScreenRepository {
    override val playerState: Flow<CurrentTrackState> =
        uiRepository.playerState.map { playerStateMapper.map(it) }

    override val errorEvents: Flow<UIError> = uiRepository.errorEvents.map {
        playerDataToUIErrorMapper.map(it)
    }

}