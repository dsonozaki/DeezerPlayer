package com.sonozaki.deezerplayer.mappers.player

import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import com.sonozaki.data.player.entities.TrackInfo
import com.sonozaki.deezerplayer.mappers.Mapper
import javax.inject.Inject

class TrackInfoToMediaItemMapper @Inject constructor() : Mapper<TrackInfo, MediaItem> {
    @OptIn(UnstableApi::class)
    override fun map(t1: TrackInfo) = MediaItem.Builder().setUri(t1.uri).setMediaMetadata(
        MediaMetadata.Builder().setTitle(t1.title).setArtist(t1.artist)
            .setArtworkUri(Uri.parse(t1.imageUri)).build()
    ).build()

}