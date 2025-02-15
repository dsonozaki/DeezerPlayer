package com.sonozaki.tracklist.domain.usecases

import com.sonozaki.tracklist.domain.entities.Track
import com.sonozaki.tracklist.domain.repository.TracksRepository
import javax.inject.Inject

class SetTrackListAndTrackUseCaseImpl @Inject constructor(private val repository: TracksRepository) :
    SetTrackListAndTrackUseCase {
    override suspend fun invoke(trackList: List<Track>, id: Int) {
        repository.setTrackListAndTrack(trackList, id)
    }

}