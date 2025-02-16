package com.sonozaki.deezerplayer.mappers.player

import android.net.Uri
import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import android.provider.MediaStore
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
        buildMetadata(t1)
    ).build()

    private fun buildMetadata(t1: TrackInfo): MediaMetadata {
        val initialItemBuilder = MediaMetadata.Builder().setTitle(t1.title).setArtist(t1.artist)
                .setExtras(Bundle().apply { putString(IMAGE_KEY, t1.imageUri) })
        return if (VERSION.SDK_INT >= Build.VERSION_CODES.Q && t1.imageUri.startsWith(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI.toString())) {
            initialItemBuilder.build()
        } else {
            initialItemBuilder.setArtworkUri(Uri.parse(t1.imageUri)).build()
        }
    }

    companion object {
        const val IMAGE_KEY = "image"
    }

}