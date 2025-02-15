package com.sonozaki.features.localtracks.presentation.fragment

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_AUDIO
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sonozaki.core.presentation.askPermission
import com.sonozaki.core.presentation.launchLifecycleAwareCoroutine
import com.sonozaki.features.localtracks.R
import com.sonozaki.features.localtracks.presentation.LocalTrackRouter
import com.sonozaki.features.localtracks.presentation.events.Event
import com.sonozaki.features.localtracks.presentation.viewmodel.LocalTracksViewModel
import com.sonozaki.tracklist.presentation.TrackFragment
import com.sonozaki.tracklist.presentation.adapter.TrackAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Fragment for displaying local tracks data.
 */
@AndroidEntryPoint
class LocalTracksFragment : TrackFragment() {

    private val navController by lazy { findNavController() }

    @Inject
    override lateinit var trackAdapterFactory: TrackAdapter.Factory

    /**
     * Router for launching player
     */
    @Inject
    lateinit var localTrackRouter: LocalTrackRouter

    /**
     * Permission required to access music on device
     */
    private val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        READ_MEDIA_AUDIO
    } else {
        READ_EXTERNAL_STORAGE
    }

    override val viewModel: LocalTracksViewModel by viewModels<LocalTracksViewModel>()


    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.handleLocalTracksIntent(LocalTracksViewModel.Intent.Initialize)
        } else {
            viewModel.handleLocalTracksIntent(LocalTracksViewModel.Intent.DenyPermission)
        }
    }

    /**
     * Build dialog for permission request.
     */
    private fun buildExplanationDialog() {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle(R.string.provide_permission)
            .setMessage(R.string.perm_rationale)
            .setPositiveButton(R.string.ok) { _, _ ->
                requestPermissionLauncher.launch(permission)
            }
            .setNegativeButton(R.string.no) { _, _ ->
                viewModel.handleLocalTracksIntent(LocalTracksViewModel.Intent.DenyPermission)
            }
            .show()
    }

    /**
     * Observe events from viewmodel
     */
    private fun observeEvents() {
        viewLifecycleOwner.launchLifecycleAwareCoroutine {
            viewModel.eventFlow.collect {
                when (it) {
                    Event.PERMISSION_DENIED_REQUEST -> requestPermissionLauncher.launch(permission)
                    Event.PERMISSION_DENIED_REQUEST_AGAIN -> buildExplanationDialog()
                    Event.OPEN_PLAYER -> localTrackRouter.openPlayerFromLocal(navController)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeEvents()
        //Ask permission to access music
        askPermission(requireActivity(), permission,
            {
                viewModel.handleLocalTracksIntent(LocalTracksViewModel.Intent.ShowPermissionExplanationDialog)
            }, {
                viewModel.handleLocalTracksIntent(LocalTracksViewModel.Intent.ShowPermissionDialog)
            },
            {
                viewModel.handleLocalTracksIntent(LocalTracksViewModel.Intent.Initialize)
            }
        )
        super.onViewCreated(view, savedInstanceState)
    }
}