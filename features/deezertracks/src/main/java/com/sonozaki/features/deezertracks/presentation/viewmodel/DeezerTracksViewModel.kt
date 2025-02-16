package com.sonozaki.features.deezertracks.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.sonozaki.features.deezertracks.presentation.events.Event
import com.sonozaki.tracklist.domain.usecases.GetTracksUseCase
import com.sonozaki.tracklist.domain.usecases.RefreshUseCase
import com.sonozaki.tracklist.domain.usecases.SetTrackListAndTrackUseCase
import com.sonozaki.tracklist.presentation.TrackViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

/**
 * Viewmodel for displaying tracks from deezer.
 */
@HiltViewModel
class DeezerTracksViewModel @Inject constructor(
    @Named(DEEZER_INJECTION_NAME) private val refreshUseCase: RefreshUseCase,
    @Named(DEEZER_INJECTION_NAME) private val setTrackListAndTrackUseCase: SetTrackListAndTrackUseCase,
    @Named(DEEZER_INJECTION_NAME) getTracksUseCase: GetTracksUseCase,
    private val eventChannel: Channel<Event>,
) : TrackViewModel(refreshUseCase, getTracksUseCase, setTrackListAndTrackUseCase) {

    init {
        viewModelScope.launch {
            refreshUseCase("")
        }
    }

    val eventFlow = eventChannel.receiveAsFlow() //events flow

    override suspend fun openPlayer() {
        eventChannel.send(Event.OPEN_PLAYER)
    }

    companion object {
        const val DEEZER_INJECTION_NAME = "deezerTracks"
    }
}