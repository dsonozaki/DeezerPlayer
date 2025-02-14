package com.sonozaki.deezerplayer.mappers.player

import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import com.sonozaki.data.player.entities.TrackInfo
import com.sonozaki.deezerplayer.mappers.Mapper
import javax.inject.Inject

class MediaItemToPlayerDataMapper @Inject constructor() : Mapper<MediaItem, TrackInfo> {
    @OptIn(UnstableApi::class)
    override fun map(t1: MediaItem) = TrackInfo(
        uri = t1.requestMetadata.mediaUri.toString(),
        artist = t1.mediaMetadata.artist.toString(),
        title = t1.mediaMetadata.title.toString(),
        imageUri = t1.mediaMetadata.artworkUri.toString()
    )
}