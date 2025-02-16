package com.sonozaki.features.deezertracks.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sonozaki.core.presentation.launchLifecycleAwareCoroutine
import com.sonozaki.features.deezertracks.DeezerRouter
import com.sonozaki.features.deezertracks.presentation.events.Event
import com.sonozaki.features.deezertracks.presentation.viewmodel.DeezerTracksViewModel
import com.sonozaki.tracklist.presentation.TrackFragment
import com.sonozaki.tracklist.presentation.adapter.TrackAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Fragment for displaying deezer tracks data.
 */
@AndroidEntryPoint
class DeezerTracksFragment : TrackFragment() {

    private val navController by lazy { findNavController() }

    @Inject
    override lateinit var trackAdapterFactory: TrackAdapter.Factory

    override val viewModel by viewModels<DeezerTracksViewModel>()

    @Inject
    lateinit var router: DeezerRouter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeEvents()
        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * Observe events from viewmodel
     */
    private fun observeEvents() {
        viewLifecycleOwner.launchLifecycleAwareCoroutine {
            viewModel.eventFlow.collect {
                when (it) {
                    Event.OPEN_PLAYER -> router.openPlayerFromDeezer(navController)
                }
            }
        }
    }
}