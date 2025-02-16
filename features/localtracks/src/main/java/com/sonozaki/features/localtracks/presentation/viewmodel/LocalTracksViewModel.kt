package com.sonozaki.features.localtracks.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.sonozaki.core.presentation.UIError
import com.sonozaki.features.localtracks.R
import com.sonozaki.features.localtracks.presentation.events.Event
import com.sonozaki.tracklist.domain.entities.TrackState
import com.sonozaki.tracklist.domain.usecases.GetTracksUseCase
import com.sonozaki.tracklist.domain.usecases.RefreshUseCase
import com.sonozaki.tracklist.domain.usecases.SetTrackListAndTrackUseCase
import com.sonozaki.tracklist.presentation.TrackViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

/**
 * Viewmodel for displaying local tracks data.
 */
@HiltViewModel
class LocalTracksViewModel @Inject constructor(
    @Named(INJECTION_NAME) private val refreshUseCase: RefreshUseCase,
    @Named(INJECTION_NAME) private val setTrackListAndTrackUseCase: SetTrackListAndTrackUseCase,
    @Named(INJECTION_NAME) getTracksUseCase: GetTracksUseCase,
    private val eventChannel: Channel<Event>,
    @Named(PERMISSION_DENIED) private val permissionDeniedChannel: Channel<Unit>
) : TrackViewModel(refreshUseCase, getTracksUseCase, setTrackListAndTrackUseCase) {

    private var wasInitialized = false //was initial data loaded?

    private var permissionDenied = false //was permission to access music files denied?

    //Flow of events
    val eventFlow = eventChannel.receiveAsFlow()

    private val permissionDeniedFlow = permissionDeniedChannel.receiveAsFlow()


    override val tracks: StateFlow<TrackState> =
        merge(getTracksUseCase(), permissionDeniedFlow.map {
            TrackState.Error(UIError(R.string.permission_denied))
        } //add specific exception in case user denied permission
        ).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            TrackState.Loading
        )

    override suspend fun openPlayer() {
        eventChannel.send(Event.OPEN_PLAYER)
    }

    /**
     * Function to load initial data. Can be called only once.
     */
    private suspend fun initialize() {
        if (!wasInitialized) {
            permissionDenied = false
            wasInitialized = true
            refreshUseCase("")
        }
    }

    /**
     * Avoid refreshing data if permission was denied
     */
    override suspend fun refresh(query: String) {
        if (!permissionDenied) {
            super.refresh(query)
        } else {
            requestPermission()
        }
    }

    /**
     * Send specific error if permission was denied
     */
    private suspend fun denyPermission() {
        permissionDenied = true
        permissionDeniedChannel.send(Unit)
    }

    private suspend fun requestPermission() {
        eventChannel.send(Event.PERMISSION_DENIED_REQUEST)
    }

    fun handleLocalTracksIntent(intent: Intent) {
        viewModelScope.launch {
            when (intent) {
                is Intent.DenyPermission -> denyPermission()
                is Intent.ShowPermissionDialog -> requestPermission()
                is Intent.ShowPermissionExplanationDialog -> eventChannel.send(
                    Event.PERMISSION_DENIED_REQUEST_AGAIN
                )

                is Intent.Initialize -> initialize()
            }
        }
    }

    /**
     * Actions specific for local tracks screen
     */
    sealed class Intent {
        /**
         * Show dialog for asking permissions first time
         */
        data object ShowPermissionDialog : Intent()

        /**
         * Show dialog for asking permissions repeatedly with explanation
         */
        data object ShowPermissionExplanationDialog : Intent()

        /**
         * Show error when user denied permission
         */
        data object DenyPermission : Intent()

        /**
         * Load initial data
         */
        data object Initialize : Intent()
    }

    companion object {
        const val INJECTION_NAME = "localTracks"
        const val PERMISSION_DENIED = "permissionDenied"
    }

}