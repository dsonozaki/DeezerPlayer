package com.sonozaki.deezerplayer.mappers.tracklist

import com.sonozaki.core.presentation.UIError
import com.sonozaki.data.localtracks.entitities.TrackData
import com.sonozaki.data.localtracks.entitities.TrackError
import com.sonozaki.data.localtracks.entitities.TracksStateData
import com.sonozaki.deezerplayer.mappers.Mapper
import com.sonozaki.tracklist.domain.entities.Track
import com.sonozaki.tracklist.domain.entities.TrackState
import javax.inject.Inject

class TrackStateDataToTrackStateMapper @Inject constructor(
    private val trackDataToTrackMapper: Mapper<TrackData, Track>,
    private val trackErrorToUIErrorMapper: Mapper<TrackError, UIError>
) : Mapper<TracksStateData, TrackState> {
    override fun map(t1: TracksStateData): TrackState =
        when (t1) {
            is TracksStateData.Loading -> TrackState.Loading
            is TracksStateData.Data -> TrackState.Data(t1.tracks.map { trackDataToTrackMapper.map(it) })
            is TracksStateData.Error -> TrackState.Error(trackErrorToUIErrorMapper.map(t1.error))
        }


}