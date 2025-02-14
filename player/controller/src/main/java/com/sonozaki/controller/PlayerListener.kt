package com.sonozaki.controller

import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import com.sonozaki.controller.domain.entities.PlayerError
import com.sonozaki.controller.domain.usecases.SendExceptionUseCase
import com.sonozaki.controller.domain.usecases.SetCurrentTrackUIUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject


class PlayerListener @Inject constructor(
    private val setCurrentTrackUIUseCase: SetCurrentTrackUIUseCase,
    private val sendExceptionUseCase: SendExceptionUseCase,
    private val scope: CoroutineScope
) : Player.Listener {

    /**
     * Send data about current media to all receivers
     */
    override fun onMediaItemTransition(
        mediaItem: MediaItem?,
        reason: Int,
    ) {
        scope.launch {
            if (mediaItem == null) {
                sendExceptionUseCase(PlayerError.NullTrack)
            } else {
                setCurrentTrackUIUseCase(mediaItem)
            }
        }
    }

    /**
     * Send data about player errors to all receivers
     */
    override fun onPlayerError(error: PlaybackException) {
        scope.launch {
            sendExceptionUseCase(PlayerError.PlaybackException)
        }
    }

    /**
     * Clear coroutine scope. Must be called when player lifecycle ends.
     */
    fun clear() {
        scope.cancel()
    }

}