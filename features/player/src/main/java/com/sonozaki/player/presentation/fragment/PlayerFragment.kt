package com.sonozaki.player.presentation.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.Player
import coil3.load
import coil3.request.error
import coil3.request.placeholder
import coil3.request.transformations
import coil3.transform.Transformation
import com.google.android.material.snackbar.Snackbar
import com.sonozaki.core.presentation.launchLifecycleAwareCoroutine
import com.sonozaki.player.R
import com.sonozaki.player.databinding.ExoplayerLayoutBinding
import com.sonozaki.player.di.LocalPlayerModule.Companion.BIG_ICON_TRANSFORMATION
import com.sonozaki.player.domain.entities.CurrentTrackState
import com.sonozaki.player.presentation.viewmodels.PlayerVM
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class PlayerFragment : Fragment() {

    @Inject
    lateinit var playerProvider: Lazy<Player> //thread-safe lazy source of media controller

    @Inject
    @Named("DefaultDispatcher")
    lateinit var defaultDispatcher: CoroutineDispatcher

    @Inject
    @Named("MainDispatcher")
    lateinit var mainDispatcher: CoroutineDispatcher

    @Inject
    @Named(BIG_ICON_TRANSFORMATION)
    lateinit var imageTransformation: Transformation


    @Inject
    lateinit var placeholderDrawable: Drawable

    private val viewModel by viewModels<PlayerVM>()

    private var _binding: ExoplayerLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = ExoplayerLayoutBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPlayer()
        observeErrors()
        observeTrackState()
    }

    /**
     * Receive player in background and set up playerView
     */
    private fun setupPlayer() {
        viewLifecycleOwner.lifecycleScope.launch(defaultDispatcher) {
            val player = playerProvider.get()
            withContext(mainDispatcher) {
                binding.playerView.player = player
            }
        }
    }

    /**
     * Displaying errors
     */
    private fun observeErrors() {
        viewLifecycleOwner.launchLifecycleAwareCoroutine {
            viewModel.errorEvents.collect {
                showSnackBar(it.textResId)
            }
        }
    }

    private fun showSnackBar(@StringRes textRes: Int) {
        Snackbar.make(binding.root, requireContext().getString(textRes), Snackbar.LENGTH_LONG)
            .show()
    }

    /**
     * Loading track cover
     */
    private fun ExoplayerLayoutBinding.loadCover(imageUri: String) {
        cover.load(imageUri) {
            placeholder(placeholderDrawable)
            error(R.drawable.baseline_music_note_24)
            transformations(imageTransformation)
        }
    }


    /**
     * Collecting and displaying track data
     */
    private fun observeTrackState() {
        viewLifecycleOwner.launchLifecycleAwareCoroutine {
            viewModel.trackPlayingState.collect {
                with(binding) {
                    displayTrackData(it)
                }
            }
        }
    }

    private fun ExoplayerLayoutBinding.displayTrackData(trackState: CurrentTrackState) {
        when (trackState) {
            is CurrentTrackState.Loading -> setupLoadingState(true)
            is CurrentTrackState.Data -> {
                setupLoadingState(false)
                artist.text = trackState.artist
                title.text = trackState.title
                loadCover(trackState.imageUri)
            }
        }
    }

    /**
     * Changing loading state
     */
    private fun setupLoadingState(loading: Boolean) {
        val progressVisibility = if (loading) {
            View.VISIBLE
        } else {
            View.GONE
        }
        val uiVisibility = if (loading) {
            View.GONE
        } else {
            View.VISIBLE
        }
        with(binding) {
            linearLayout.visibility = uiVisibility
            cover.visibility = uiVisibility
            progressbar.visibility = progressVisibility
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}