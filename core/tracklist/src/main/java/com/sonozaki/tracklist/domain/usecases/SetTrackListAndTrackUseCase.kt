package com.sonozaki.tracklist.domain.usecases

import com.sonozaki.tracklist.domain.entities.Track

interface SetTrackListAndTrackUseCase {
    suspend operator fun invoke(trackList: List<Track>, id: Int)
}