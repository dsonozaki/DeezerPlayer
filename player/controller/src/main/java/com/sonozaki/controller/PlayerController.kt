package com.sonozaki.controller

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.sonozaki.controller.domain.entities.PlayerEvent
import com.sonozaki.controller.domain.usecases.GetEventFlowUseCase
import dagger.Lazy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Class for sending data to player.
 */
@Singleton
class PlayerController @Inject constructor(
    private val getEventFlowUseCase: GetEventFlowUseCase,
    private val playerListener: PlayerListener,
    @Named("IODispatcher") private val ioDispatcher: CoroutineDispatcher,
    private val playerProvider: Lazy<Player> //thread-safe lazy source of media controller
) {

    private var controller: Player? = null

    /**
     * Get controller or initialize it in background.
     */
    private suspend fun getOrInitController(): Player = withContext(ioDispatcher) {
        if (controller == null) {
            controller = playerProvider.get().also {
                it.addListener(playerListener)
            }
        }
        controller!!
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
        val ctrl = getOrInitController()
        ctrl.setMediaItems(playList)
        ctrl.seekTo(track, 0)
        ctrl.prepare()
        ctrl.play()
    }

    /**
     * Release resources. Must be called in the end of player lifecycle.
     */
    fun clear() {
        playerListener.clear()
        controller?.release()
        controller = null
    }

    companion object {
        /**
         * Create media controller. Must be created in background thread and initialized lazily.
         */
        fun buildMediaController(context: Context, sessionToken: SessionToken): Player =
            MediaController.Builder(context, sessionToken).buildAsync().get()
    }
}