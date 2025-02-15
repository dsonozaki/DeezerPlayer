package com.sonozaki.tracklist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonozaki.tracklist.domain.entities.TrackState
import com.sonozaki.tracklist.domain.usecases.GetTracksUseCase
import com.sonozaki.tracklist.domain.usecases.RefreshUseCase
import com.sonozaki.tracklist.domain.usecases.SetTrackListAndTrackUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Abstract viewmodel for all TrackFragments
 */
abstract class TrackViewModel(
    private val refreshUseCase: RefreshUseCase,
    getTracksUseCase: GetTracksUseCase,
    private val setTrackListAndTrackUseCase: SetTrackListAndTrackUseCase
) : ViewModel() {

    /**
     * Tracks data
     */
    open val tracks = getTracksUseCase().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        TrackState.Loading
    )

    /**
     * Send playlist and track id to player and open player screen
     */
    private suspend fun setTrackListAndTrack(trackId: Int) {
        val tracksState = tracks.value
        if (tracksState is TrackState.Data) {
            setTrackListAndTrackUseCase(tracksState.tracks, trackId)
        }
        openPlayer()
    }

    /**
     * Open player screen
     */
    abstract suspend fun openPlayer()

    /**
     * Load new data
     */
    protected open suspend fun refresh(query: String) {
        refreshUseCase(query)
    }


    fun handleIntent(intent: Intent) {
        viewModelScope.launch {
            when (intent) {
                is Intent.Refresh -> refresh(intent.searchQuery)
                is Intent.OpenTrack -> setTrackListAndTrack(intent.id)
            }
        }
    }

    sealed class Intent {
        data class Refresh(val searchQuery: String) : Intent()
        data class OpenTrack(val id: Int) : Intent()
    }
}