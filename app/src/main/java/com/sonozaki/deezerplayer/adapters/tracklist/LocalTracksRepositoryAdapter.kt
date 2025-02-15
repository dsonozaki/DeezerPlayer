package com.sonozaki.deezerplayer.adapters.tracklist

import com.sonozaki.data.localtracks.entitities.TracksStateData
import com.sonozaki.data.localtracks.repository.LocalTracksRepository
import com.sonozaki.data.player.entities.TrackInfo
import com.sonozaki.data.player.repositories.PlayerEventsRepository
import com.sonozaki.deezerplayer.mappers.Mapper
import com.sonozaki.tracklist.domain.entities.Track
import com.sonozaki.tracklist.domain.entities.TrackState
import com.sonozaki.tracklist.domain.repository.TracksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalTracksRepositoryAdapter @Inject constructor(
    private val playerEventsRepository: PlayerEventsRepository,
    private val localTracksRepository: LocalTracksRepository,
    private val mapper: Mapper<Track, TrackInfo>,
    private val tracksStateDataToTrackStateMapper: Mapper<TracksStateData, TrackState>
) : TracksRepository {
    override val tracksState: Flow<TrackState> =
        localTracksRepository.tracks.map { tracksStateDataToTrackStateMapper.map(it) }

    override suspend fun refresh(searchQuery: String) {
        localTracksRepository.refreshData(searchQuery)
    }

    override suspend fun setTrackListAndTrack(trackList: List<Track>, id: Int) {
        playerEventsRepository.selectPlaylistAndTrack(trackList.map { mapper.map(it) }, id)
    }
}