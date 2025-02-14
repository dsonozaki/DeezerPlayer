package com.sonozaki.data.player.repositories

import com.sonozaki.data.player.entities.PlayerEventData
import com.sonozaki.data.player.entities.TrackInfo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

class PlayerEventsRepositoryImpl @Inject constructor(private val eventChannel: Channel<PlayerEventData>) :
    PlayerEventsRepository {
    override val eventFlow: Flow<PlayerEventData> = eventChannel.receiveAsFlow()

    override suspend fun selectPlaylistAndTrack(playList: List<TrackInfo>, track: Int) {
        eventChannel.send(PlayerEventData.SelectPlaylistAndTrack(playList, track))
    }
}