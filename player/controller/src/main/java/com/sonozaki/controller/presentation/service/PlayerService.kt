package com.sonozaki.controller.presentation.service

import android.app.PendingIntent
import android.content.Intent
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Media session service.
 */
@AndroidEntryPoint
class PlayerService : MediaSessionService() {

    @Inject
    lateinit var mainActivityIntent: PendingIntent

    private var mediaSession: MediaSession? = null

    /**
     * Create player and mediaSession inside service.
     */
    override fun onCreate() {
        super.onCreate()
        val player = ExoPlayer.Builder(applicationContext).build()
        mediaSession =
            MediaSession.Builder(this, player).setSessionActivity(mainActivityIntent).build()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? =
        mediaSession

    override fun onTaskRemoved(rootIntent: Intent?) {
        val player = mediaSession?.player
        if (!player?.playWhenReady!!) {
            player.pause()
        }
        stopSelf()
    }

    override fun onDestroy() {
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }
}