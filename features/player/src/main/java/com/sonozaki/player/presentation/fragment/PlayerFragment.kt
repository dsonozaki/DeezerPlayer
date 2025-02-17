package com.sonozaki.player.presentation.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import coil3.load
import coil3.request.error
import coil3.request.placeholder
import coil3.request.transformations
import coil3.transform.Transformation
import com.google.android.material.snackbar.Snackbar
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import com.sonozaki.core.presentation.launchLifecycleAwareCoroutine
import com.sonozaki.player.databinding.ExoplayerLayoutBinding
import com.sonozaki.player.di.LocalPlayerModule.Companion.BIG_ICON_TRANSFORMATION
import com.sonozaki.player.domain.entities.CurrentTrackState
import com.sonozaki.player.presentation.viewmodels.PlayerVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class PlayerFragment : Fragment() {

    @Inject
    lateinit var sessionToken: SessionToken

    @Inject
    @Named("DefaultDispatcher")
    lateinit var defaultDispatcher: CoroutineDispatcher

    @Inject
    @Named("MainDispatcher")
    lateinit var mainDispatcher: CoroutineDispatcher

    @Inject
    @Named(BIG_ICON_TRANSFORMATION)
    lateinit var imageTransformation: Transformation

    private var mediaControllerFuture: ListenableFuture<MediaController>? = null


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
        observeErrors()
        observeTrackState()
    }

    /**
     * Receive player in background and set up playerView
     */
    @OptIn(UnstableApi::class)
    private fun setupPlayer() {
        mediaControllerFuture = MediaController.Builder(requireContext(), sessionToken).buildAsync()
        mediaControllerFuture?.apply {
            addListener ({
                val controller = get()
                binding.playerView.player = controller
                binding.playerView.showController()
                Log.w("player","final")
            }, MoreExecutors.directExecutor())
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
            error(com.sonozaki.resources.R.drawable.baseline_music_note_24)
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
                binding.artist.isSelected = true
                title.text = trackState.title
                binding.title.isSelected = true
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

    override fun onStart() {
        super.onStart()
        setupPlayer()
    }

    override fun onStop() {
        mediaControllerFuture?.let {
            MediaController.releaseFuture(it)
        }
        super.onStop()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}