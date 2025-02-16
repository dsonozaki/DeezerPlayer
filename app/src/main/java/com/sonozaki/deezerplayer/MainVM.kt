package com.sonozaki.deezerplayer

import com.sonozaki.controller.PlayerController
import com.sonozaki.controller.presentation.viewmodel.PlayerListenerVM
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(playerController: PlayerController) :
    PlayerListenerVM(playerController) {
    /**
     * Bind player to viewmodel lifecycle. Without that MediaSessionService may go to background and be destroyed after 1 minute if user pauses music and move app to background.
     * You can call this method safely from activity.
     */
    fun startService() {
        observePlayerEvents()
    }
}