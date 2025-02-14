package com.sonozaki.player.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonozaki.player.domain.entities.CurrentTrackState
import com.sonozaki.player.domain.usecases.GetErrorEventsUseCase
import com.sonozaki.player.domain.usecases.GetPlayerStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PlayerVM @Inject constructor(
    getPlayerStateUseCase: GetPlayerStateUseCase,
    getErrorEventsUseCase: GetErrorEventsUseCase
) : ViewModel() {

    val errorEvents = getErrorEventsUseCase()

    val trackPlayingState = getPlayerStateUseCase().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        CurrentTrackState.Loading
    )
}