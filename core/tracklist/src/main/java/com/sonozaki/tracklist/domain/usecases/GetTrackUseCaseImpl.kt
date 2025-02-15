package com.sonozaki.tracklist.domain.usecases

import com.sonozaki.tracklist.domain.entities.TrackState
import com.sonozaki.tracklist.domain.repository.TracksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrackUseCaseImpl @Inject constructor(private val repository: TracksRepository) :
    GetTracksUseCase {
    override operator fun invoke(): Flow<TrackState> = repository.tracksState
}