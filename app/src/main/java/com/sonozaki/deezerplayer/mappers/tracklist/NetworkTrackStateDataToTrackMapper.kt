package com.sonozaki.deezerplayer.mappers.tracklist

import com.sonozaki.core.presentation.UIError
import com.sonozaki.deezerplayer.mappers.Mapper
import com.sonozaki.deezertracks.entitities.NetworkTrackStateData
import com.sonozaki.deezertracks.network.dto.TrackDto
import com.sonozaki.network.NetworkError
import com.sonozaki.tracklist.domain.entities.Track
import com.sonozaki.tracklist.domain.entities.TrackState
import javax.inject.Inject

class NetworkTrackStateDataToTrackMapper @Inject constructor(
    private val trackDataToTrackMapper: Mapper<TrackDto, Track>,
    private val networkErrorUIErrorMapper: Mapper<NetworkError, UIError>
) : Mapper<NetworkTrackStateData, TrackState> {
    override fun map(t1: NetworkTrackStateData) = when (t1) {
        is NetworkTrackStateData.Data -> TrackState.Data(t1.tracks.map {
            trackDataToTrackMapper.map(it)
        }
        )

        is NetworkTrackStateData.Error -> TrackState.Error(networkErrorUIErrorMapper.map(t1.error))
        NetworkTrackStateData.Loading -> TrackState.Loading
    }
}