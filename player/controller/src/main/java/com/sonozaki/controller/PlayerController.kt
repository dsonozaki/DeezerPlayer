package com.sonozaki.controller

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.sonozaki.controller.domain.entities.PlayerEvent
import com.sonozaki.controller.domain.usecases.GetEventFlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

/**
 * Class for sending data to player. It can be used to bind player lifecycle to lifecycle of some component.
 */
class PlayerController (
    private val context: Context,
    private val sessionToken: SessionToken,
    private val getEventFlowUseCase: GetEventFlowUseCase,
    private val playerListener: PlayerListener,
    private val ioDispatcher: CoroutineDispatcher
) {

    private var controller: Player? = null

    private val mutex: Mutex = Mutex()

    /**
     * Get or init player
     */
    private suspend fun getPlayer(): Player = withContext(ioDispatcher) {
        mutex.withLock {
            if (controller == null) {
                controller = MediaController.Builder(context, sessionToken).buildAsync().get()?.apply {
                    addListener(playerListener)
                }
            }
            controller!!
        }
    }

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
    private suspend fun selectPlaylistAndTrack(
        playList: List<MediaItem>,
        track: Int
    ) {
        val player = getPlayer()
        val position = getPosition(player, playList, track)
        player.setMediaItems(playList)
        player.seekTo(track, position)
        player.prepare()
        player.play()
        }


    /**
     * Start song from the same position if user selects song that's playing now
     */
    private fun getPosition(
        player: Player,
        playList: List<MediaItem>,
        track: Int
    ) =
        if (playList[track].localConfiguration?.uri != player.currentMediaItem?.localConfiguration?.uri) {
            0
        } else {
            player.currentPosition
        }

    /**
     * Release player resources
     */
    private fun releaseResources() {
        controller?.removeListener(playerListener)
        controller?.release()
        controller = null
    }

    /**
     * Release resources. Must be called in the end of player lifecycle.
     */
    fun clear() {
        releaseResources()
        playerListener.clear()
   }
}