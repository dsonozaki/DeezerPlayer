package com.sonozaki.deezerplayer.mappers.tracklist

import com.sonozaki.data.localtracks.entitities.TrackData
import com.sonozaki.deezerplayer.mappers.Mapper
import com.sonozaki.tracklist.domain.entities.Track
import javax.inject.Inject

class TrackDataToTrackMapper @Inject constructor() : Mapper<TrackData, Track> {
    override fun map(t1: TrackData) = Track(
        t1.uri, t1.duration, t1.coverUri, t1.title, t1.artist
    )


}