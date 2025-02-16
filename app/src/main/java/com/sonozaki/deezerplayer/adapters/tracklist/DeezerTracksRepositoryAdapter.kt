package com.sonozaki.deezerplayer.adapters.tracklist

import com.sonozaki.data.player.entities.TrackInfo
import com.sonozaki.data.player.repositories.PlayerEventsRepository
import com.sonozaki.deezerplayer.mappers.Mapper
import com.sonozaki.deezertracks.entitities.NetworkTrackStateData
import com.sonozaki.deezertracks.repository.DeezerTracksRepository
import com.sonozaki.tracklist.domain.entities.Track
import com.sonozaki.tracklist.domain.entities.TrackState
import com.sonozaki.tracklist.domain.repository.TracksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeezerTracksRepositoryAdapter @Inject constructor(
    private val playerEventsRepository: PlayerEventsRepository,
    private val deezerTracksRepository: DeezerTracksRepository,
    private val trackMapper: Mapper<Track, TrackInfo>,
    private val networkTrackStateDataToTrackMapper: Mapper<NetworkTrackStateData, TrackState>
) : TracksRepository {
    override val tracksState: Flow<TrackState>
        get() = deezerTracksRepository.tracks.map { networkTrackStateDataToTrackMapper.map(it) }

    override suspend fun refresh(searchQuery: String) {
        deezerTracksRepository.refreshData(searchQuery)
    }

    override suspend fun setTrackListAndTrack(trackList: List<Track>, id: Int) {
        playerEventsRepository.selectPlaylistAndTrack(trackList.map { trackMapper.map(it) }, id)
    }
}