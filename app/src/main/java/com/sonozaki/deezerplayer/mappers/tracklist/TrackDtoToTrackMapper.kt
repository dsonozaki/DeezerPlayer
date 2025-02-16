package com.sonozaki.deezerplayer.mappers.tracklist

import com.sonozaki.deezerplayer.mappers.Mapper
import com.sonozaki.deezertracks.network.dto.TrackDto
import com.sonozaki.tracklist.domain.entities.Track
import javax.inject.Inject

class TrackDtoToTrackMapper @Inject constructor() : Mapper<TrackDto, Track> {
    override fun map(t1: TrackDto): Track = Track(
        t1.preview,
        t1.duration * 1000,
        t1.album.pictureMedium,
        t1.title,
        t1.artist.name
    )
}