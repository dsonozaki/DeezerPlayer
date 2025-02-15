package com.sonozaki.deezerplayer.mappers.tracklist

import com.sonozaki.data.player.entities.TrackInfo
import com.sonozaki.deezerplayer.mappers.Mapper
import com.sonozaki.tracklist.domain.entities.Track
import javax.inject.Inject

class TrackToTrackInfoMapper @Inject constructor() : Mapper<Track, TrackInfo> {
    override fun map(t1: Track) = TrackInfo(
        uri = t1.uri,
        artist = t1.artist,
        title = t1.title,
        imageUri = t1.coverUri
    )
}