package com.sonozaki.controller.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonozaki.controller.PlayerController
import kotlinx.coroutines.launch

/**
 * Extend this class to tie media service lifecycle to lifecycle of your viewodel.
 */
open class PlayerListenerVM(private val playerController: PlayerController) : ViewModel() {

    private var wasInitialized: Boolean = false

    protected fun observePlayerEvents() {
        if (wasInitialized) return
        wasInitialized = true
        viewModelScope.launch {
            playerController.collectEvents()
        }
    }

    override fun onCleared() {
        playerController.clear()
        super.onCleared()
    }
}