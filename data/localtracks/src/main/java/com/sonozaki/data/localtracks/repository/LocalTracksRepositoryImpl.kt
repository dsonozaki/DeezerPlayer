package com.sonozaki.data.localtracks.repository

import android.content.ContentUris
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import com.sonozaki.data.localtracks.entitities.TrackData
import com.sonozaki.data.localtracks.entitities.TrackError
import com.sonozaki.data.localtracks.entitities.TracksStateData
import com.sonozaki.data.localtracks.exceptions.DataRetrievalException
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class LocalTracksRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val tracksStateChannel: Channel<TracksStateData>,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher
) : LocalTracksRepository {


    private val audioProjection = arrayOf(
        MediaStore.Audio.AudioColumns.ARTIST,
        MediaStore.Audio.AudioColumns.TITLE,
        MediaStore.Audio.AudioColumns.DURATION,
        MediaStore.Audio.AudioColumns.ALBUM_ID,
        MediaStore.Audio.AudioColumns._ID
    )

    private val albumProjection =
        arrayOf(MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART)

    private val sortOrder = MediaStore.Audio.Media.DEFAULT_SORT_ORDER

    override val tracks: Flow<TracksStateData> = tracksStateChannel.receiveAsFlow()

    /**
     * Get album cover by album id on devices with SDK < 29.
     */
    private suspend fun getAlbumCoverOnOldDevices(albumId: Long): String {
        try {
            val cursor = context.contentResolver.query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                albumProjection,
                MediaStore.Audio.Albums._ID + "=?",
                arrayOf(albumId.toString()),
                null
            )
            cursor?.use {
                val albumArtColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ART) //column is deprecated, but it's the only way to get album art on old devices ¯\_(ツ)_/¯
                return if (cursor.moveToFirst()) {
                    cursor.getString(albumArtColumn)
                } else {
                    ""
                }
            }
            return ""
        } catch (e: Exception) {
            return ""
        }
    }

    /**
     * Query the storage for music files. Finds data about tracks with title or artist name match given query.
     * @throws DataRetrievalException if cursor is null
     */
    private suspend fun getMusicData(query: String): List<TrackData> = withContext(ioDispatcher) {
        val selection =
            "${MediaStore.Audio.AudioColumns.IS_MUSIC} = 1 AND (${MediaStore.Audio.AudioColumns.TITLE} LIKE ? OR ${MediaStore.Audio.AudioColumns.ARTIST} LIKE ?)"
        //query data about music files
        val musicCursor = context.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            audioProjection,
            selection,
            arrayOf("%$query%", "%$query%"),
            sortOrder
        )

        musicCursor?.use { cursor ->
            //get column indexes
            val artistIndex =
                cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST)
            val titleIndex =
                cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE)
            val durationIndex =
                cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION)
            val albumIdIndex =
                cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM_ID)
            val idIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns._ID)
            return@use buildList {
                //iterate table and add new music items
                while (cursor.moveToNext()) {
                    val audioId = cursor.getLong(idIndex)
                    val audioArtist = cursor.getString(artistIndex)
                    val audioTitle = cursor.getString(titleIndex)
                    val audioDuration = cursor.getLong(durationIndex)
                    val albumId = cursor.getLong(albumIdIndex)
                    //get album cover uri
                    val contentUri = getAlbumCover(albumId)
                    //create song uri from provided audio id
                    val songUri = ContentUris.withAppendedId(
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        audioId
                    ).toString()
                    add(
                        TrackData(
                            songUri,
                            audioDuration,
                            contentUri,
                            audioTitle,
                            audioArtist
                        )
                    )
                }
            }
        } ?: throw DataRetrievalException()
    }

    /**
     * Get album cover. For devices with SDK >= 29 album cover can be loaded from album uri using coil.
     * Old devices store album cover uri in content provider.
     */
    private suspend fun getAlbumCover(albumId: Long) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //build album uri
            ContentUris.withAppendedId(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                albumId
            ).toString()
        } else {
            getAlbumCoverOnOldDevices(albumId)
        }

    override suspend fun refreshData(query: String) {
        tracksStateChannel.send(TracksStateData.Loading)
        try {
            val data = getMusicData(query)
            tracksStateChannel.send(TracksStateData.Data(data))
        } catch (e: DataRetrievalException) {
            tracksStateChannel.send(TracksStateData.Error(TrackError.DataNotRetrieved))
        } catch (e: Exception) {
            tracksStateChannel.send(TracksStateData.Error(TrackError.CustomException(e.stackTraceToString())))
        }
    }
}