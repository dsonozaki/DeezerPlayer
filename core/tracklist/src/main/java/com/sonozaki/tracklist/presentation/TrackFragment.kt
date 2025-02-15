package com.sonozaki.tracklist.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.sonozaki.core.presentation.launchLifecycleAwareCoroutine
import com.sonozaki.tracklist.databinding.TracksLayoutBinding
import com.sonozaki.tracklist.domain.entities.TrackState
import com.sonozaki.tracklist.presentation.adapter.TrackAdapter
/**
 * Abstract Fragment for displaying track data
 */
abstract class TrackFragment : Fragment() {

    abstract val trackAdapterFactory: TrackAdapter.Factory

    private var _binding: TracksLayoutBinding? = null
    private val binding get() = _binding!!

    protected abstract val viewModel: TrackViewModel


    protected open val adapter: TrackAdapter by lazy {
        trackAdapterFactory.create {
            viewModel.handleIntent(TrackViewModel.Intent.OpenTrack(it))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = TracksLayoutBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeSearch()
        setupAdapter()
        displayData()
        setupButtons()
        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * Setup retry button
     */
    private fun setupButtons() {
        binding.retryButton.setOnClickListener {
            refresh()
        }
    }

    private fun refresh() {
        viewModel.handleIntent(TrackViewModel.Intent.Refresh(binding.search.query.toString()))
    }

    /**
     * Load new data when user entered new search request
     */
    private fun observeSearch() {
        with(binding) {
            search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        refresh()
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    /**
     * Display tracks data
     */
    private fun displayData() {
        viewLifecycleOwner.launchLifecycleAwareCoroutine {
            viewModel.tracks.collect {
                showState(it)
                when (it) {
                    is TrackState.Data -> submitData(it)
                    is TrackState.Loading -> {}
                    is TrackState.Error -> setErrorText(it)
                }
            }
        }
    }

    private fun setErrorText(it: TrackState.Error) {
        binding.errorText.text = it.error.getString(requireContext())
    }

    private fun submitData(it: TrackState.Data) {
        adapter.submitList(it.tracks)
    }

    private fun showState(state: TrackState) {
        val errorVisibility = if (state is TrackState.Error) View.VISIBLE else View.GONE
        val progressVisibility = if (state is TrackState.Loading) View.VISIBLE else View.GONE
        val dataVisibility = if (state is TrackState.Data) View.VISIBLE else View.GONE
        with(binding) {
            error.visibility = errorVisibility
            progress.visibility = progressVisibility
            tracksView.visibility = dataVisibility
        }
    }

    private fun setupAdapter() {
        with(binding) {
            tracksView.adapter = adapter
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}