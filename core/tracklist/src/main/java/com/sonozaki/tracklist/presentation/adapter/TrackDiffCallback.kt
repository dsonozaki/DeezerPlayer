package com.sonozaki.tracklist.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.sonozaki.tracklist.domain.entities.Track
import javax.inject.Inject

class TrackDiffCallback @Inject constructor() : DiffUtil.ItemCallback<Track>() {
    override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean =
        oldItem.uri == newItem.uri


    override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean =
        oldItem == newItem
}