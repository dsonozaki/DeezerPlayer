package com.sonozaki.controller

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.sonozaki.controller.domain.entities.PlayerEvent
import com.sonozaki.controller.domain.usecases.GetEventFlowUseCase

/**
 * Class for sending data to player. It can be used to bind player lifecycle to lifecycle of some component.
 */
class PlayerController (
    private val context: Context,
    private val sessionToken: SessionToken,
    private val getEventFlowUseCase: GetEventFlowUseCase,
    private val playerListener: PlayerListener
) {

   private var controller: Player? = null

    private var mediaControllerFuture: ListenableFuture<MediaController>? = null


    suspend fun collectEvents() {
        getEventFlowUseCase().collect {
            when (it) {
                is PlayerEvent.SelectPlaylistAndTrack -> selectPlaylistAndTrack(
                    it.playList,
                    it.track
                )
            }
        }
    }

    /**
     * Select playlist and start track with given id.
     */
    private fun selectPlaylistAndTrack(
        playList: List<MediaItem>,
        track: Int
    ) {
        val mediaControllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        mediaControllerFuture.apply {
            addListener({
                controller = get()
                controller?.addListener(playerListener)
                val position = getPosition(playList, track)
                controller?.setMediaItems(playList)
                controller?.seekTo(track, position)
                controller?.prepare()
                controller?.play()
            }, ContextCompat.getMainExecutor(context))
        }
    }

    /**
     * Start song from the same position if user selects song that's playing now
     */
    private fun getPosition(
        playList: List<MediaItem>,
        track: Int
    ) =
        if (playList[track].localConfiguration?.uri != controller?.currentMediaItem?.localConfiguration?.uri) {
            0
        } else {
            controller?.currentPosition ?: 0
        }

    /**
     * Release resources. Must be called in the end of player lifecycle.
     */
    fun clear() {
        controller?.release()
        mediaControllerFuture?.let {
            MediaController.releaseFuture(it)
        }
        mediaControllerFuture = null
        playerListener.clear()
        controller?.removeListener(playerListener)
    }
}