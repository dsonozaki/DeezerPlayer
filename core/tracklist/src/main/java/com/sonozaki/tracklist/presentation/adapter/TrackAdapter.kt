package com.sonozaki.tracklist.presentation.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import coil3.load
import coil3.request.error
import coil3.request.placeholder
import coil3.request.transformations
import coil3.transform.Transformation
import com.sonozaki.tracklist.R
import com.sonozaki.tracklist.databinding.TrackItemBinding
import com.sonozaki.tracklist.domain.entities.Track
import com.sonozaki.tracklist.presentation.utils.formatMilliseconds
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Named

/**
 * Adapter for track element
 */
class TrackAdapter @AssistedInject constructor(
    diffCallback: TrackDiffCallback,
    private val placeholderDrawable: Drawable,
    @Named("smallIcon") private val transformation: Transformation,
    @Assisted private val onTrackClicked: (Int) -> Unit //select track and send data to player
) : ListAdapter<Track, TrackViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TrackItemBinding.inflate(inflater, parent, false)
        return TrackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = getItem(position)
        with(holder.binding) {
            cover.load(track.coverUri) {
                placeholder(placeholderDrawable)
                error(R.drawable.baseline_music_note_24)
                transformations(transformation)
            }
            duration.text = formatMilliseconds(track.duration)
            artist.text = track.artist
            title.text = track.title
            layout.setOnClickListener {
                onTrackClicked(position)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(lambda: (Int) -> Unit): TrackAdapter
    }
}