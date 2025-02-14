package com.sonozaki.data.player.repositories

import com.sonozaki.data.player.entities.PlayerErrorData
import com.sonozaki.data.player.entities.PlayerStateData
import com.sonozaki.data.player.entities.TrackInfo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class PlayerUIRepositoryImpl @Inject constructor(
    private val playerStateDataFlow: MutableStateFlow<PlayerStateData>,
    private val errorEventsChannel: Channel<PlayerErrorData>
) : PlayerUIRepository {
    override val playerState: StateFlow<PlayerStateData>
        get() = playerStateDataFlow.asStateFlow()

    override val errorEvents: Flow<PlayerErrorData>
        get() = errorEventsChannel.receiveAsFlow()

    override suspend fun sendException(exception: PlayerErrorData) {
        errorEventsChannel.send(exception)
    }

    override suspend fun setCurrentTrack(trackInfo: TrackInfo) {
        playerStateDataFlow.emit(PlayerStateData.Data(trackInfo))
    }

}